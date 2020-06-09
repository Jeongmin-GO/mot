package com.example.mot.ui.base

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment : Fragment() {
    protected val disposables by lazy { CompositeDisposable() }

    override fun onDestroy() {
        if (!disposables.isDisposed) disposables.clear()
        super.onDestroy()
    }
}