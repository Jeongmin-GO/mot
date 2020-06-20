package com.example.mot.unit.extension

import android.content.Context
import android.view.Menu
import android.view.View
import android.widget.Toast

val Any.TAG : String
    get() { return javaClass.simpleName }

fun View.show() = View.VISIBLE
fun View.hide() = View.GONE
fun Context.toast(msg: CharSequence) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
