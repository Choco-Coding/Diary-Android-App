# Diary with Weather Information Android App Project
Kotlin을 기반으로 하여 안드로이드 앱을 제작하였습니다. 일상을 기록할 수 있는 다이어리 앱입니다. 홈 화면에서는 날씨와 날짜 정보가 표시됩니다.

Compose를 이용하여 화면을 구성하였고, Room 라이브러리를 통해 데이터베이스를 구현하여 데이터를 저장합니다. Retrofit을 이용하여 기상청 오픈 api로부터 날씨 정보를 불러옵니다.

*SERVICE_KEY에 오픈 api 인증키를 작성해야 날씨 정보가 표시됩니다.

***
## Result Screen

1. 홈 화면: 오늘의 날씨, 날짜 정보와 가장 최근 작성한 일기가 표시됩니다.
<img width="260" alt="result_home" src="https://github.com/Choco-Coding/Diary-with-Weather-Information-Android-App/assets/117694927/07e66b97-77d1-49d9-9ee3-fa685cee963f">

2. 일기 목록 화면: 작성한 전체 일기가 목록으로 표시됩니다.
<img width="259" alt="result_list" src="https://github.com/Choco-Coding/Diary-with-Weather-Information-Android-App/assets/117694927/106e5b58-04e3-47a3-bc23-db939c0ad5df">

3. 일기 상세 정보 화면: 일기의 상세 정보를 표시합니다. 공유, 삭제, 수정 기능이 있습니다. 
<img width="258" alt="result_details" src="https://github.com/Choco-Coding/Diary-with-Weather-Information-Android-App/assets/117694927/12942cd0-2e5d-4164-9a58-94a8ba036ff8">

4. 일기 공유 화면: 일기 공유 버튼을 눌렀을 경우의 공유 창입니다.
<img width="259" alt="result_share" src="https://github.com/Choco-Coding/Diary-with-Weather-Information-Android-App/assets/117694927/96d4bad8-403b-4edd-90fe-52f29329fcef">

5. 일기 삭제 화면: 일기 삭제 버튼을 눌렀을 경우 나타나는 일기 삭제 확인 창입니다.
<img width="260" alt="result_delete" src="https://github.com/Choco-Coding/Diary-with-Weather-Information-Android-App/assets/117694927/1da4d652-726f-4751-9bba-5c7e37684ebd">

6. 일기 수정 화면: 저장된 일기의 정보를 수정하는 화면입니다.
<img width="258" alt="result_edit" src="https://github.com/Choco-Coding/Diary-with-Weather-Information-Android-App/assets/117694927/6e497768-24f0-4162-8e24-1b6c64758f54">

7. 일기 작성 화면: 새로운 일기를 작성하여 저장할 때의 화면입니다.
<img width="260" alt="result_write" src="https://github.com/Choco-Coding/Diary-with-Weather-Information-Android-App/assets/117694927/75dfed7f-82d5-4721-a0dd-a736f78b9c79">

8. 키보드가 나타난 일기 작성 화면: Text Field를 눌렀을 경우 키보드가 나타납니다.
<img width="260" alt="result_write2" src="https://github.com/Choco-Coding/Diary-with-Weather-Information-Android-App/assets/117694927/fc50ca83-10af-4610-8670-51f15f2edf65">

9. Dark 모드를 실행했을 경우의 홈 화면입니다.
<img width="256" alt="result_dark" src="https://github.com/Choco-Coding/Diary-with-Weather-Information-Android-App/assets/117694927/bf116f16-4d9e-4f0f-b5ca-dfde6cd9cb5e">
