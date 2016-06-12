package com.yodle.android.kotlindemo.dagger

import com.yodle.android.kotlindemo.TestMainApp
import com.yodle.android.kotlindemo.activity.MainActivityTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(TestAppModule::class))
interface TestAppComponent : AppComponent {

    fun inject(testMainApp: TestMainApp)

    fun inject(mainActivityTest: MainActivityTest)
}