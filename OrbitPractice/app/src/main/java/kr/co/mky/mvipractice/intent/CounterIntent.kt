package kr.co.mky.mvipractice.intent

sealed interface CounterIntent {
    data object Up: CounterIntent
    data object Down: CounterIntent
}