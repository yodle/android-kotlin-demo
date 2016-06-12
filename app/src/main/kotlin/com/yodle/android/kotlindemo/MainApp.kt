package com.yodle.android.kotlindemo

import android.app.Application
import com.yodle.android.kotlindemo.dagger.AppComponent
import com.yodle.android.kotlindemo.dagger.AppModule
import com.yodle.android.kotlindemo.dagger.DaggerAppComponent
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber

open class MainApp : Application() {

    companion object {
        @JvmStatic lateinit var graph: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        JodaTimeAndroid.init(this)

        initializeGraph()
    }

    open fun initializeGraph() {
        graph = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        graph.inject(this)
    }
}