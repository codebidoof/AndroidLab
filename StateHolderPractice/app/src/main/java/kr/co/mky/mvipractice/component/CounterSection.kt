package kr.co.mky.mvipractice.component

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import kr.co.mky.mvipractice.intent.Intent
import kr.co.mky.mvipractice.state.CountState
import kr.co.mky.mvipractice.state.SampleStatus

@Composable
fun CounterSection(
    countState: CountState,
    onIntent: (Intent) -> Unit,
) {
    Log.d("MyApp", "CounterSection 리컴포지션")
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        if (countState.status is SampleStatus.Loading) {
            CircularProgressIndicator()
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { onIntent(Intent.Down) }
            ) {
                Icon(Icons.Default.Remove, contentDescription = "감소")
            }

            Text(countState.number.toString())

            Button(
                onClick = { onIntent(Intent.Up) }
            ) {
                Icon(Icons.Default.Add, contentDescription = "증가")
            }
        }
    }
}