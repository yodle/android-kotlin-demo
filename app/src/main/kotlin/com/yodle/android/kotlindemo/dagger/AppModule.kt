package com.yodle.android.kotlindemo.dagger

import com.yodle.android.kotlindemo.MainApp
import com.yodle.android.kotlindemo.service.GitHubApiService
import com.yodle.android.kotlindemo.service.GitHubService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val mainApp: MainApp) {

    @Provides
    @Singleton
    fun provideMainApp() = mainApp

    @Provides
    @Singleton
    fun provideGitHubApiService() = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
            .create(GitHubApiService::class.java)

    @Provides
    @Singleton
    fun provideGitHubService() = GitHubService();

}