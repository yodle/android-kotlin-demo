package com.yodle.android.kotlindemo.extension

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

fun Context.inflateLayout(resource: Int, root: ViewGroup? = null, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(this).inflate(resource, root, attachToRoot)
}

inline fun Context.alert(func: AlertDialog.Builder.() -> AlertDialog.Builder) {
    AlertDialog.Builder(this).func().show()
}

fun Context.toastShort(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun Context.toastShort(resId: Int) = Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()

fun Context.toastLong(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()

fun Context.toastLong(resId: Int) = Toast.makeText(this, resId, Toast.LENGTH_LONG).show()
