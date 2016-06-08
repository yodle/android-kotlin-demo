package com.yodle.android.kotlindemo.extension

import com.trello.rxlifecycle.ActivityEvent
import com.trello.rxlifecycle.ActivityLifecycleProvider
import com.trello.rxlifecycle.kotlin.bindUntilEvent
import rx.Observable
import rx.Observer
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

fun <T> Observable<T>.subscribeOnIo() = this.subscribeOn(Schedulers.io())

inline fun <T> Observable<T>.subscribeUntilDestroy(activity: ActivityLifecycleProvider, observer: KObserver<T>.() -> Unit): Subscription {
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
