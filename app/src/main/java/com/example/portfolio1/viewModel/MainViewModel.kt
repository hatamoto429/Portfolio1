package com.example.portfolio1.viewModel

import android.app.Application
import android.widget.Toast
import android.os.Bundle
import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.portfolio1.database.entities.Picture
import com.example.portfolio1.database.entities.Result
import com.example.portfolio1.database.entities.Welcome
import com.example.portfolio1.webAPI.ktorHttpClient
import com.example.portfolio1.webAPI.randomUserAPI
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val internalUser = MutableLiveData("")
    private val userdata = MutableLiveData<Welcome>()
    val welcomeData: LiveData<Welcome> = userdata
    val allUser: LiveData<String> = internalUser
    private val api = randomUserAPI(ktorHttpClient, 10)


    fun loadAllUser(count: Int) {

        viewModelScope.launch {
            internalUser.postValue("")
            userdata.postValue(null)
            val users = api.get(count)
            userdata.postValue(users)
            //internalUser.postValue(Json.encodeToString(Welcome.serializer(), users))
            //internalUser.postValue(users.results[0].name.first)
            //internalUser.postValue(welcomeData.value.results[0].name.first)
        }
    }

    fun resetAllUser() {
        internalUser.postValue("")
    }

    @Composable
    fun ShowSomeText(text: String){
        Text (text = text)
    }


    @Composable
        fun DisplayUser(welcome: Welcome?, modifier: Modifier = Modifier) {
        welcome?.results?.forEach() {
            Button (
                onClick = {
                },
                modifier = Modifier.background(Color.White, RectangleShape)
                    .size(450.dp, 50.dp)
                    .border(1.dp, Color.Black),
            ){
                Text(text = it.name.title)
                Text(text = it.name.first)
                Text(text = it.name.last)
            }
        }
        //ImageView(

        //Picasso.get().load(user.picture.medium).into(imageView)
        //)
    }

}
