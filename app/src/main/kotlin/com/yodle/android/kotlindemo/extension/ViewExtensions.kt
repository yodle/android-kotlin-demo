package com.yodle.android.kotlindemo.extension

import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView

fun View.showIf(show: Boolean) {
    if (show) {
        show()
    } else {
        hide()
    }
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

inline fun WebView.setProgressChangedListener(crossinline onProgressChanged: (Int) -> Unit) {
    this.setWebChromeClient(object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            onProgressChanged.invoke(newProgress)
        }
    })
}
