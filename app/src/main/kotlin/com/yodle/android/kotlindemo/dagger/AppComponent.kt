package com.yodle.android.kotlindemo.dagger

import com.yodle.android.kotlindemo.MainApp
import com.yodle.android.kotlindemo.activity.MainActivity
import com.yodle.android.kotlindemo.activity.RepositoryDetailActivity
import com.yodle.android.kotlindemo.service.GitHubService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun inject(mainApp: MainApp)

    fun inject(mainActivity: MainActivity)

    fun inject(repositoryDetailActivity: RepositoryDetailActivity)

    fun inject(gitHubService: GitHubService)

}