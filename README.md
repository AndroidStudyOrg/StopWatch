# StopWatch
 스톱워치 앱
## 구현기능
- 0.1초마다 숫자 업데이트
- 시작, 일시정지, 정지
- 정지 전 다이얼로그 알람
- 시작 전 카운트다운
- 카운트 다운 3초 전 알림음
- 랩타임 기록
## 학습 내용
- ProgressBar
- AlertDialog
- Thread
- runOnUiThread
- ToneGenerator
- addView
### Thread 동작 원리
1. 앱이 실행되면 MainThread가 필수적으로 생긴다. 이 Thread에서 UI 관련 작업을 한다.
2. MainThread가 아닌 UI에 접근할 수 없는 WorkerThread가 생기게 되면 이 Thread에서 UI를 조작하는 것이 아닌, MainThread에 있는 Handler에게 [Message] 또는 [Runnable한 객체]를 보내서 메세지나 실행하고자 하는 동작을 Handling 해달라고 보낸다.
3. MainThread의 Handler는 Message Queue에 Message를 넣고, Looper는 Message Queue의 내용을 확인해서 Message가 들어왔으면 메세지를 처리하거나, Runnable한 객체가 들어왔으면 실행해달라고 요청한다.
- 이렇게 되면 UI 관련 작업은 모두 MainThread에서 진행할 수 있다.
- UI Thread를 차단시키면 안된다는 규칙 또한 WorkerThread를 통해 지킬 수 있다.

### 방법
- Activity의 runOnUiThread: 이 부분에는 실행하고 싶은 Runnable한 객체를 넣을 수 있다.
- View의 post(Runnable)
- View의 postDelayed(Runnable, Long): Runnable한 실행 단위를 ms 후에 실행해라.
- Handler: 직접 Handler를 구현해서 Handler.post로 Runnable 객체를 보낼 수도 있고, Handler.sendMessage로 메세지를 보낼 수도 있다.


https://github.com/AndroidStudyOrg/StopWatch/assets/51990139/066e4c2e-6c81-4521-a54f-daa6bb177309

