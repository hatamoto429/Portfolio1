package com.example.portfolio1.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SettingsViewModel (application: Application) : AndroidViewModel(application) {
    val currentGeneratedUserCount = MutableLiveData<Int?>(10)
    val userGenCount: LiveData<Int?> = currentGeneratedUserCount
}