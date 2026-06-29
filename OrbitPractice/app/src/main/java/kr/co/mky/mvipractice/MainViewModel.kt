package kr.co.mky.mvipractice

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.co.mky.mvipractice.intent.CounterIntent
import kr.co.mky.mvipractice.sideeffect.SideEffect
import kr.co.mky.mvipractice.state.CountState

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(CountState(0))
    val state = _state.asStateFlow()

    private val _effect = Channel<SideEffect>()
    val effect = _effect.receiveAsFlow()

    fun handleIntent(intent: CounterIntent) {
        Log.d("MyApp", "ViewModel이 $intent 인텐트 수신")
        when (intent) {
            CounterIntent.Up -> increase()
            CounterIntent.Down -> decrease()
        }
    }

    private fun increase() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    number = it.number + 1
                )
            }
            Log.d(
                "MyApp",
                "State 업데이트 완료. 현재 숫자: ${_state.value.number}"
            )
        }
    }

    private fun decrease() {
        viewModelScope.launch {
            if (_state.value.number == 0) {
                _effect.send(SideEffect.ShowToast("0 미만으로는 줄일 수 없어요!"))
                Log.d(
                    "MyApp",
                    "0 이하로 감소 불가. SideEffect 발생"
                )
            }
            else {
                _state.update {
                    it.copy(
                        number = it.number - 1
                    )
                }
                Log.d(
                    "MyApp",
                    "State 업데이트 완료. 현재 숫자: ${_state.value.number}"
                )
            }
        }
    }
}