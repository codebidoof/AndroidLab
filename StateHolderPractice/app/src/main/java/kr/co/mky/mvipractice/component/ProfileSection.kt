package kr.co.mky.mvipractice.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kr.co.mky.mvipractice.intent.Intent
import kr.co.mky.mvipractice.state.ProfileState
import kr.co.mky.mvipractice.state.SampleStatus
import kr.co.mky.mvipractice.ui.theme.MVIPracticeTheme

@Composable
fun ProfileSection(
    profileState: ProfileState,
    modifier: Modifier = Modifier,
    onIntent: (Intent) -> Unit
) {
    Log.d("MyApp", "ProfileSection 리컴포지션")

    Column(
        modifier = modifier.clickable {
            onIntent(Intent.RefreshUser)
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (profileState.status is SampleStatus.Loading) {
            CircularProgressIndicator()
        } else {
            Text(text = "이름: ${profileState.name}")
            Text(text = "닉네임: ${profileState.nickName}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileSectionPreview() {
    MVIPracticeTheme {
        ProfileSection(
            profileState = ProfileState(
                name = "홍길동",
                nickName = "쾌도난마"
            ),
            onIntent = {}
        )
    }
}