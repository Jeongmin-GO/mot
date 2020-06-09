package com.example.mot.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mot.db.entity.Menu
import com.example.mot.extension.TAG
import com.example.mot.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MenuViewModel (application: Application) : ViewModel() {

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MenuViewModel(application) as T
        }
    }

    private val repository: Repository = Repository(application)
    private val disposable: CompositeDisposable = CompositeDisposable()

    private val menus: LiveData<MutableList<Menu>> by lazy {
        repository.getAllMenu()
    }

    fun getAllMenu() = menus

    fun getMenuByCategory(cat: Long) : LiveData<MutableList<Menu>> {
        return repository.getMenuByCategory(cat)
    }

    fun insertMenu(menu: Menu) {
        repository.insertCategory(menu)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.e(TAG, "insert success")},
                { Log.e(TAG, "insert Failed")}
            )
            .apply { disposable.add(this) }
    }

    fun deleteMenu(menu: Menu) {
        repository.deleteCategory(menu)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .apply { disposable.add(this) }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}