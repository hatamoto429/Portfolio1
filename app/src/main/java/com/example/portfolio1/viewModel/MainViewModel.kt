package com.example.portfolio1.viewModel

import android.app.Application
import android.widget.Toast
import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.portfolio1.webAPI.ktorHttpClient
import com.example.portfolio1.webAPI.randomUserAPI
import kotlinx.coroutines.launch

class MainViewModel (application: Application) : AndroidViewModel(application)
{

    private val internalUser = MutableLiveData("")
    val allUser: LiveData<String> = internalUser
    private val api = randomUserAPI(ktorHttpClient)



   fun loadAllUser(count: Int) {

       viewModelScope.launch { internalUser.postValue("")
           val user = api.get(count)
           internalUser.postValue(user)
       }
   }

   fun resetAllUser() {
       internalUser.postValue("")
   }




}
