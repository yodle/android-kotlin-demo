package com.yodle.android.kotlindemo

import com.yodle.android.kotlindemo.dagger.DaggerTestAppComponent
import com.yodle.android.kotlindemo.dagger.TestAppModule

open class TestMainApp : MainApp() {

    override fun initializeGraph() {
        graph = DaggerTestAppComponent.builder().testAppModule(TestAppModule()).build()
    }
}
