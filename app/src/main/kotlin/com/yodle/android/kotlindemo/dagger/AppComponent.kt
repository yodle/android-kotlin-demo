package com.yodle.android.kotlindemo.dagger

import com.yodle.android.kotlindemo.MainApp
import com.yodle.android.kotlindemo.activity.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun inject(mainApp: MainApp)

    fun inject(mainActivity: MainActivity)

}