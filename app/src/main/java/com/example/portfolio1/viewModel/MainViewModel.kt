package com.example.portfolio1.viewModel

import android.app.Application
import android.widget.Toast
import android.os.Bundle
import android.widget.ImageView
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.portfolio1.database.entities.Picture
import com.example.portfolio1.database.entities.Result
import com.example.portfolio1.database.entities.Welcome
import com.example.portfolio1.webAPI.ktorHttpClient
import com.example.portfolio1.webAPI.randomUserAPI
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class MainViewModel (application: Application) : AndroidViewModel(application)
{

    private val internalUser = MutableLiveData("")
    val allUser: LiveData<String> = internalUser
    private val api = randomUserAPI(ktorHttpClient, 10)


   fun loadAllUser(count: Int) {
       viewModelScope.launch {
           internalUser.postValue("")
           val users = api.get(count)
           users.results.forEach(){

           }
           internalUser.postValue(Json.encodeToString(Welcome.serializer(), users))
           internalUser.postValue(users.results[0].name.first)
       }
   }

   fun resetAllUser() {
       internalUser.postValue("")
   }



    @Composable
    fun Image(
        //bitmap or vector or painter
        contentDescription: String?,
        modifier: Modifier = Modifier,
        alignment: Alignment = Alignment.Center,
    ){

    }

    @Composable
    fun String(
        //bitmap or vector or painter
        name: String?,
        modifier: Modifier = Modifier,
        alignment: Alignment = Alignment.Center,
    ){

    }

    @Composable
    fun DisplayUser(user: Result){

    ImageView(

        //Picasso.get().load(user.picture.medium).into(imageView)
    )
    Text(text = name)
   }


}
