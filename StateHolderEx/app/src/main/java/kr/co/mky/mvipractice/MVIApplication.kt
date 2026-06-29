package kr.co.mky.mvipractice

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MVIApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}