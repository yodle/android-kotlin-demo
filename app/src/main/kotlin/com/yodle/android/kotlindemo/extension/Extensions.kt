package com.yodle.android.kotlindemo.extension

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.support.v7.graphics.Palette
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ImageView
import android.widget.Toast
import com.ocpsoft.pretty.time.PrettyTime
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import com.trello.rxlifecycle.ActivityEvent
import com.trello.rxlifecycle.ActivityLifecycleProvider
import com.trello.rxlifecycle.kotlin.bindUntilEvent
import org.joda.time.DateTime
import rx.Observable
import rx.Observer
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.text.NumberFormat

fun Long?.formatted() = if (this != null) NumberFormat.getInstance().format(this) else null

fun Context.inflateLayout(resource: Int, root: ViewGroup? = null, attachToRoot: Boolean = false): View {
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

fun Context.toastShort(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun Context.toastShort(resId: Int) = Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()

fun Context.toastLong(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()

fun Context.toastLong(resId: Int) = Toast.makeText(this, resId, Toast.LENGTH_LONG).show()

fun WebView.setProgressChangedListener(onProgressChanged: (Int) -> Unit) {
    this.setWebChromeClient(object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            onProgressChanged.invoke(newProgress)
        }
    })
}

fun PrettyTime.format(isoString: String) = this.format(DateTime(isoString).toDate())

fun ImageView.generatePalette(listener: (Palette) -> Unit) {
    Palette.from((this.drawable as BitmapDrawable).bitmap).generate(listener)
}

fun ImageView.loadUrl(url: String) = Picasso.with(this.context).load(url).into(this)

fun ImageView.loadUrl(url: String, callback: KCallback.() -> Unit) = Picasso.with(this.context).load(url).intoWithCallback(this, callback)

fun RequestCreator.intoWithCallback(target: ImageView, callback: KCallback.() -> Unit) {
    this.into(target, KCallback().apply(callback))
}

class KCallback : Callback {

    private var onSuccess: (() -> Unit)? = null
    private var onError: (() -> Unit)? = null

    override fun onSuccess() {
        onSuccess?.invoke()
    }

    override fun onError() {
        onError?.invoke()
    }

    fun onSuccess(function: () -> Unit) {
        this.onSuccess = function
    }

    fun onError(function: () -> Unit) {
        this.onError = function
    }
}

fun <T> Observable<T>.subscribeOnIo() = this.subscribeOn(Schedulers.io())

fun <T> Observable<T>.subscribeUntilDestroy(activity: ActivityLifecycleProvider, observer: KObserver<T>.() -> Unit): Subscription {
    return this.observeOn(AndroidSchedulers.mainThread())
            .bindUntilEvent(activity, ActivityEvent.DESTROY)
            .subscribe(KObserver<T>().apply(observer))
}

class KObserver<T> : Observer<T> {

    private var onNext: ((T) -> Unit)? = null
    private var onError: ((Throwable) -> Unit)? = null
    private var onCompleted: (() -> Unit)? = null

    override fun onNext(t: T) {
        onNext?.invoke(t)
    }

    override fun onError(e: Throwable) {
        onError?.invoke(e)
    }

    override fun onCompleted() {
        onCompleted?.invoke()
    }

    fun onNext(function: (T) -> Unit) {
        this.onNext = function
    }

    fun onError(function: (Throwable) -> Unit) {
        this.onError = function
    }

    fun onCompleted(function: () -> Unit) {
        this.onCompleted = function
    }
}
