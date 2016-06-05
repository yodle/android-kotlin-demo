package com.yodle.android.kotlindemo.extension

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ImageView
import com.ocpsoft.pretty.time.PrettyTime
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import org.joda.time.DateTime
import java.text.NumberFormat

fun Long?.formatted() = if (this != null) NumberFormat.getInstance().format(this) else null

fun Context.inflateLayout(resource: Int, root: ViewGroup, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(this).inflate(resource, root, attachToRoot)
}

fun View.showIf(show: Boolean) {
    this.visibility = if (show) View.VISIBLE else View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun WebView.setProgressChangedListener(onProgressChanged: (Int) -> Unit) {
    this.setWebChromeClient(object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            onProgressChanged.invoke(newProgress)
        }
    })
}

fun ImageView.loadUrl(url: String) = Picasso.with(this.context).load(url).into(this)

fun RequestCreator.into(target: ImageView, onSuccess: () -> Unit = {}, onError: () -> Unit = {}) {
    this.into(target, object : Callback {
        override fun onSuccess() {
            onSuccess.invoke()
        }

        override fun onError() {
            onError.invoke()
        }
    })
}

fun PrettyTime.format(isoString: String) = this.format(DateTime(isoString).toDate())
