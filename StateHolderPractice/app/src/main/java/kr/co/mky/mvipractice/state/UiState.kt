package kr.co.mky.mvipractice.state

// 카운터 앱 전체의 UiState를 모아놓은 state holder
data class UiState(
    val countState: CountState,
    val profileState: ProfileState
)

// 현재 카운트 수를 나타내는 상태
data class CountState(
    val number: Int = 0,
    val status: SampleStatus  = SampleStatus.Idle,
)

// 유저 정보 상태
data class ProfileState(
    val name: String = "",
    val nickName: String = "",
    val status: SampleStatus = SampleStatus.Idle
)