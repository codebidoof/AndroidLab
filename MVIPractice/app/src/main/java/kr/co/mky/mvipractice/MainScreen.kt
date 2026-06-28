package kr.co.mky.mvipractice

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.mky.mvipractice.intent.CounterIntent
import kr.co.mky.mvipractice.sideeffect.SideEffect
import kr.co.mky.mvipractice.state.CountState
import kr.co.mky.mvipractice.ui.theme.MVIPracticeTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val state by viewModel.state
        .collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when(effect) {
                is SideEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
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
    state: CountState,
    modifier: Modifier = Modifier,
    onIntent: (CounterIntent) -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    Log.d("MyApp", "Down 인텐트 뷰모델로 전달")
                    onIntent(CounterIntent.Down)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "감소"
                )
            }

            Text(
                state.number.toString()
            )

            Button(
                onClick = {
                    Log.d("MyApp", "Up 인텐트 뷰모델로 전달")
                    onIntent(CounterIntent.Up)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "증가"
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MVIPracticeTheme {
        MainContent(
            state = CountState(number = 0),
            onIntent = {}
        )
    }
}