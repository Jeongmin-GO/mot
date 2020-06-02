package com.example.mot.ui.base

import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity : AppCompatActivity() {

    protected val disposables by lazy { CompositeDisposable() }

    override fun onDestroy() {
        if (!disposables.isDisposed) disposables.clear()
        super.onDestroy()    }

}
