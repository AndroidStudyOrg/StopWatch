package org.shop.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import org.shop.stopwatch.databinding.ActivityMainBinding
import org.shop.stopwatch.databinding.DialogCountdownSettingBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var countDownSec = 10

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
    }

    private fun start() {

    }

    private fun stop() {
        binding.fabStart.isVisible = true
        binding.fabStop.isVisible = true
        binding.fabPause.isVisible = false
        binding.fabLap.isVisible = false
    }

    private fun pause() {

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