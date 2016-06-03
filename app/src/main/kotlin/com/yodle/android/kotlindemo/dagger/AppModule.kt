package com.yodle.android.kotlindemo.dagger

import com.yodle.android.kotlindemo.MainApp
import com.yodle.android.kotlindemo.service.GitHubService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val mainApp: MainApp) {

    @Provides
    @Singleton
    fun provideMainApp() = mainApp

    @Provides
    @Singleton
    fun provideGitHubService() = GitHubService(mainApp);

}