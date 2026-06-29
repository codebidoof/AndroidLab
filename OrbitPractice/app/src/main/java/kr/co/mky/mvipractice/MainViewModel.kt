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

    fun increase() = intent {
        reduce { state.copy(isLoading = true) }

        counterRepository.increase(state.number)
            .onSuccess { number ->
                reduce { state.copy(number = number, isLoading = false) }
            }
    }

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