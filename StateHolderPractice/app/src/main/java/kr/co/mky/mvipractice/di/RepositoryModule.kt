package kr.co.mky.mvipractice.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.mky.mvipractice.CounterRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCounterRepository(): CounterRepository {
        return CounterRepository()
    }
}