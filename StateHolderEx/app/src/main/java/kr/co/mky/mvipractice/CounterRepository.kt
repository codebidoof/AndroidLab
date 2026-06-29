package kr.co.mky.mvipractice

import kotlinx.coroutines.delay

class CounterRepository {

    suspend fun increase(current: Int): Result<Int>  = runCatching {
        delay(1000) // 네트워크 요청 시뮬레이션
        current + 1
    }


    suspend fun decrease(current: Int): Result<Int> = runCatching {
        delay(1000)
        current - 1
    }

}