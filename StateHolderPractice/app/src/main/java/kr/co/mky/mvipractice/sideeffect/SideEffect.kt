package kr.co.mky.mvipractice.sideeffect

sealed interface SideEffect {
    data class ShowToast(val message: String): SideEffect
}