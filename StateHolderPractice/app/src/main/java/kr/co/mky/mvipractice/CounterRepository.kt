package kr.co.mky.mvipractice

import kotlinx.coroutines.delay
import kr.co.mky.mvipractice.model.Profile

class CounterRepository {

    suspend fun increase(current: Int): Result<Int>  = runCatching {
        delay(1000) // 네트워크 요청 시뮬레이션
        current + 1
    }

    suspend fun decrease(current: Int): Result<Int> = runCatching {
        delay(1000)
        current - 1
    }

    // 추가!
    suspend fun getProfile(): Result<Profile> = runCatching {
        delay(1000)

        Profile(
            name = "현우",
            nickName = "엠키"
        )

    }

}