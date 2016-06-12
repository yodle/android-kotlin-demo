package com.yodle.android.kotlindemo

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner

class EspressoTestRunner : AndroidJUnitRunner() {

    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, TestMainApp::class.java.name, context)
    }
}
