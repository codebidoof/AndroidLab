package kr.co.mky.mvipractice.state

data class CountState(
    val number: Int,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
