package com.example.portfolio1.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.portfolio1.webAPI.ktorHttpClient
import com.example.portfolio1.webAPI.RandomUserApi
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class MainViewModel (application: Application) : AndroidViewModel(application)
{
    private val internalUser = MutableLiveData("")
    val allUser: LiveData<String> = internalUser
    private val api = RandomUserApi(ktorHttpClient)

    fun loadAllUser(count: Int) {

        viewModelScope.launch {
            internalUser.postValue("")
           // val user = api.get(count)
           // internalUser.postValue(Json.encodeToString(UserResults.serializer(), user))
        }
    }

    fun resetAllUser() {
        internalUser.postValue("")
    }




}
