package kr.co.mky.mvipractice.state



sealed interface SampleStatus {
    data object Idle : SampleStatus
    data object Loading : SampleStatus
    data class Error(val message: String) : SampleStatus
}

