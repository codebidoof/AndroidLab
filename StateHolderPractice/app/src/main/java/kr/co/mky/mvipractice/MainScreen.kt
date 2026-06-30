package kr.co.mky.mvipractice

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kr.co.mky.mvipractice.component.CounterSection
import kr.co.mky.mvipractice.component.ProfileSection
import kr.co.mky.mvipractice.intent.Intent
import kr.co.mky.mvipractice.sideeffect.SideEffect
import kr.co.mky.mvipractice.state.CountState
import kr.co.mky.mvipractice.state.ProfileState
import kr.co.mky.mvipractice.state.SampleStatus
import kr.co.mky.mvipractice.state.UiState
import kr.co.mky.mvipractice.ui.theme.MVIPracticeTheme
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    // 상태 구독
    val state = viewModel.collectAsState().value

    // effect 수신
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SideEffect.ShowToast -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    MainContent(
        state = state,
        modifier = modifier,
        onIntent = viewModel::handleIntent
    )

}

@Composable
fun MainContent(
    state: UiState,
    modifier: Modifier = Modifier,
    onIntent: (Intent) -> Unit,
) {
    Log.d("MyApp", "MainContent 리컴포지션")
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CounterSection(
            countState = state.countState,
            onIntent = onIntent
        )

        Spacer(Modifier.height(24.dp))

        ProfileSection(
            profileState = state.profileState,
            onIntent = onIntent
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MVIPracticeTheme {
        MainContent(
            state = UiState(
                countState = CountState(
                    number = 10,
                    status = SampleStatus.Idle
                ),
                profileState = ProfileState(
                    name = "문현우",
                    nickName = "엠키",
                    status = SampleStatus.Idle
                )
            ),
            onIntent = {}
        )
    }
}


