package com.example.mot.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mot.db.entity.Category
import com.example.mot.extension.TAG
import com.example.mot.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CategoryViewModel(application: Application) : ViewModel() {

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CategoryViewModel(application) as T
        }
    }

    private val repository: Repository = Repository(application)
    private val disposable: CompositeDisposable = CompositeDisposable()

    private val category: LiveData<MutableList<Category>> by lazy {
        repository.getCategory()
    }

    fun getMenuCategory() = category

    fun getCategoryNameById(catName: String):LiveData<Category> = repository.getCategoryIdByName(catName)

    fun insertCategory(cat: Category) {
        repository.insertCategory(cat)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.e(TAG, "insert success")},
                { Log.e(TAG, "insert Failed")}
            )
            .apply { disposable.add(this) }
    }

    fun deleteCategory(cat: Category) {
        repository.deleteCategory(cat)
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