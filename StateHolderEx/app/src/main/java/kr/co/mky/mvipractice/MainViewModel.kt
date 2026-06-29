package kr.co.mky.mvipractice

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kr.co.mky.mvipractice.sideeffect.SideEffect
import kr.co.mky.mvipractice.state.CountState
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel
class MainViewModel @Inject constructor(
    private val counterRepository: CounterRepository
) : ViewModel(), ContainerHost<CountState, SideEffect> {

    override val container: Container<CountState, SideEffect> = container(
        initialState = CountState(0),
    )

    // 카운터를 1 증가시킨다.
    // 요청 중 로딩 상태를 활성화하며, 완료 후 새 값으로 갱신한다.
    fun increase() = intent {
        reduce { state.copy(isLoading = true) }

        counterRepository.increase(state.number)
            .onSuccess { number ->
                reduce { state.copy(number = number, isLoading = false) }
            }
    }

    // 카운터를 1 감소시킨다.
    // 현재 값이 0이면 감소 없이 토스트 [SideEffect]를 발행한다.
    fun decrease() = intent {
        if (state.number == 0) {
            postSideEffect(
                SideEffect.ShowToast("0 미만으로는 줄일 수 없어요!")
            )
            return@intent
        }

        reduce {
            state.copy(isLoading = true)
        }

        counterRepository.decrease(state.number)
            .onSuccess { number ->
                reduce {
                    state.copy(
                        number = number,
                        isLoading = false
                    )
                }
            }
    }
}