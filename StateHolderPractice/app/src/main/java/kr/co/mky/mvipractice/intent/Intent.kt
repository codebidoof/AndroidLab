package kr.co.mky.mvipractice.intent

sealed interface Intent {
    data object Up: Intent
    data object Down: Intent
    data object RefreshUser: Intent
}