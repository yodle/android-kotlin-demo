package com.yodle.android.kotlindemo.dagger

import com.yodle.android.kotlindemo.service.GitHubApiService
import com.yodle.android.kotlindemo.service.GitHubService
import dagger.Module
import dagger.Provides
import org.mockito.Mockito.mock
import javax.inject.Singleton

@Module
class TestAppModule {

    @Provides
    @Singleton
    fun provideGitHubService() = mock(GitHubService::class.java)

    @Provides
    @Singleton
    fun provideGitHubApiService() = mock(GitHubApiService::class.java)
}
