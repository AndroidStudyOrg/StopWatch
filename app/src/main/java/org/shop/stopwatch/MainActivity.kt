package org.shop.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import org.shop.stopwatch.databinding.ActivityMainBinding
import org.shop.stopwatch.databinding.DialogCountdownSettingBinding
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // 카운트 다운 시간
    private var countDownSec = 10

    // 진행되는 카운트 다운 시간 (1 Second == 10 DeciSecond)
    private var currentCountDownDeciSec = countDownSec * 10

    // 올라가는 숫자를 체크하기 위한 변수(0.1초 단위의 숫자)
    private var currentDeciSecond = 0

    // pause에서 timer를 제어하기 위한 instance 생성
    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        binding.tvCountDown.setOnClickListener {
            showCountDownDialog()
        }

        binding.fabStart.setOnClickListener {
            start()
            binding.fabStart.isVisible = false
            binding.fabStop.isVisible = false
            binding.fabPause.isVisible = true
            binding.fabLap.isVisible = true
        }

        binding.fabStop.setOnClickListener {
            showAlertDialog()
        }

        binding.fabPause.setOnClickListener {
            pause()
            binding.fabStart.isVisible = true
            binding.fabStop.isVisible = true
            binding.fabPause.isVisible = false
            binding.fabLap.isVisible = false
        }

        binding.fabLap.setOnClickListener {
            lap()
        }

        initCountDownView()
    }

    private fun initCountDownView() {
        binding.tvCountDown.text = String.format("%02d", countDownSec)
        binding.pbCountDown.progress = 100
    }

    private fun start() {
        timer = timer(initialDelay = 0, period = 100) {
            if (currentCountDownDeciSec == 0) {
                currentDeciSecond += 1
//            Log.d("currentDeciSecond", currentDeciSecond.toString())
                val minutes = currentDeciSecond.div(10) / 60
                val second = currentDeciSecond.div(10) % 60
                val deciSecond = currentDeciSecond % 10
                /**
                 * 메인 스레드에서 call 할 수 있는 방법
                 * 1. runOnUiThread
                 * 2. View.post
                 * 3. View.postDelayed
                 * 4. Handler
                 */
                runOnUiThread {
                    binding.tvTimeText.text = String.format("%02d:%02d", minutes, second)
                    binding.tvTickText.text = deciSecond.toString()

                    binding.groupCountDown.isVisible = false
                }
            } else {
                currentCountDownDeciSec -= 1
                val seconds = currentCountDownDeciSec / 10
                val progress = (currentCountDownDeciSec / (countDownSec * 10f)) * 100

                // 어떠한 view 여도 상관 없음
                binding.root.post {
                    binding.tvCountDown.text = String.format("%02d", seconds)
                    binding.pbCountDown.progress = progress.toInt()
                }
            }
        }
    }

    private fun stop() {
        binding.fabStart.isVisible = true
        binding.fabStop.isVisible = true
        binding.fabPause.isVisible = false
        binding.fabLap.isVisible = false

        currentDeciSecond = 0
        binding.tvTimeText.text = "00:00"
        binding.tvTickText.text = "0"

        binding.groupCountDown.isVisible = true
        initCountDownView()
    }

    private fun pause() {
        timer?.cancel()
        timer = null
    }

    private fun lap() {

    }

    private fun showCountDownDialog() {
        AlertDialog.Builder(this).apply {
            val dialogBinding = DialogCountdownSettingBinding.inflate(layoutInflater).also {
                with(it.npCountDownSec) {
                    maxValue = 20
                    minValue = 0
                    value = countDownSec
                }
                setView(it.root)
            }
            setTitle("카운트다운 설정")
            setPositiveButton("확인") { dialog, id ->
                countDownSec = dialogBinding.npCountDownSec.value
                currentCountDownDeciSec = countDownSec * 10
                binding.tvCountDown.text = String.format("%02d", countDownSec)
            }
            setNegativeButton("취소", null)
        }.show()
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this).apply {
            setMessage("종료하시겠습니까?")
            setPositiveButton("네") { dialog, id ->
                stop()
            }
            setNegativeButton("아니요", null)
        }.show()
    }
}