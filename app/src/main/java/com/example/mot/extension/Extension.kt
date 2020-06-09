package com.example.mot.extension

import android.content.Context
import android.widget.Toast

val Any.TAG : String
    get() { return javaClass.simpleName }

fun Context.toast(msg: CharSequence) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
