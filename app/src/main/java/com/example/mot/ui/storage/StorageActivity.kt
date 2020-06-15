package com.example.mot.ui.storage

import android.os.Bundle
import android.os.PersistableBundle
import com.example.mot.ui.base.BaseActivity
import com.google.firebase.storage.FirebaseStorage

abstract class StorageActivity : BaseActivity() {
    lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        storage = FirebaseStorage.getInstance()
        includesForDownloadFiles()
    }

    private fun includesForDownloadFiles() {

    }
}