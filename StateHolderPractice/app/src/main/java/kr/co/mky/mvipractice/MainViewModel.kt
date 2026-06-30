package kr.co.mky.mvipractice

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.mky.mvipractice.intent.Intent
import javax.inject.Inject
import kr.co.mky.mvipractice.sideeffect.SideEffect
import kr.co.mky.mvipractice.state.CountState
import kr.co.mky.mvipractice.state.ProfileState
import kr.co.mky.mvipractice.state.SampleStatus
import kr.co.mky.mvipractice.state.UiState
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel
class MainViewModel @Inject constructor(
    private val counterRepository: CounterRepository
) : ViewModel(), ContainerHost<UiState, SideEffect> {

    override val container: Container<UiState, SideEffect> = container(
        initialState = UiState(
            countState = CountState(),
            profileState = ProfileState()
        ),
    )

    fun handleIntent(intent: Intent) {
        when(intent) {
            Intent.Down -> decrease()
            Intent.Up -> increase()
            Intent.RefreshUser -> refreshUser()
        }
    }

    private fun increase() = intent {
        reduce {
            state.copy(
                countState = state.countState.copy(
                    status = SampleStatus.Loading
                )
            )
        }

        counterRepository.increase(state.countState.number)
            .onSuccess { number ->
                reduce {
                    state.copy(
                        countState = state.countState.copy(
                            number = number,
                            status = SampleStatus.Idle
                        )
                    )
                }
            }
    }

    private fun decrease() = intent {
        if (state.countState.number == 0) {
            postSideEffect(
                SideEffect.ShowToast("0 미만으로는 줄일 수 없어요!")
            )
            return@intent
        }

        reduce {
            state.copy(
                countState = state.countState.copy(
                    status = SampleStatus.Loading
                )
            )
        }

        counterRepository.decrease(state.countState.number)
            .onSuccess { number ->
                reduce {
                    state.copy(
                        countState = state.countState.copy(
                            number = number,
                            status = SampleStatus.Idle
                        )
                    )
                }
            }
    }

    private fun refreshUser() = intent {
        reduce {
            state.copy(
                profileState = state.profileState.copy(
                    status = SampleStatus.Loading
                )
            )
        }

        counterRepository.getProfile()
            .onSuccess {
                reduce {
                    state.copy(
                        profileState = state.profileState.copy(
                            name = it.name,
                            nickName = it.nickName,
                            status = SampleStatus.Idle
                        )
                    )
                }
            }
    }

}