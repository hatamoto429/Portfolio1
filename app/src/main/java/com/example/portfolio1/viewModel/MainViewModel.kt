package com.example.portfolio1.viewModel

import android.app.Application
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.portfolio1.MainActivity
import com.example.portfolio1.R
import com.example.portfolio1.database.entities.User
import com.example.portfolio1.database.entities.Welcome
import com.example.portfolio1.repository.UserRepo
import com.example.portfolio1.webAPI.ktorHttpClient
import com.example.portfolio1.webAPI.randomUserAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userRepo: UserRepo, application: Application) :
    AndroidViewModel(application) {

    private val internalUser = MutableLiveData("")
    private val userdata = MutableLiveData<Welcome>(null)
    val welcomeData: LiveData<Welcome> = userdata
    val allUser: LiveData<String> = internalUser
    private val api = randomUserAPI(ktorHttpClient)
    private val _userDetails = MutableStateFlow<List<User>>(emptyList())
    val userDetails: StateFlow<List<User>> = _userDetails


    fun doDeleteSingleUserRecord(shaKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.deleteSingleUserRecord(shaKey)
        }
    }

    fun doGetUserDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.getUsers
                .catch { e ->
                    //Log error here
                }
                .collect {
                    _userDetails.value = it
                    _userDetails.emit(it)
                }

        }
    }

    fun resetAllUser() {
        internalUser.postValue("")
    }

    @Composable
    fun DisplayUser(
        welcome: Welcome?,
        navController: NavController,
        detailViewModel: DetailViewModel
    ) {
        doGetUserDetails()
        userDetails.value.forEach() {
            Button(
                onClick = {
                    detailViewModel._currentUser.value = it
                    navController.navigate(MainActivity.ScreenData.Detail.route)
                },
                modifier = Modifier
                    .size(450.dp, 50.dp)
                    .border(1.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
            ) {
                Text(text = it.title + " ")
                Text(text = it.userFirstname + " ")
                Text(text = it.userLastname + " ")
                //Text(text = "|", modifier = Modifier.offset(15.dp, 0.dp))
                //Text(text = it.userTelephone, modifier = Modifier.offset(30.dp, 0.dp))
                Row() {
                    Button(
                        onClick = {
                            doDeleteSingleUserRecord(it.sha256)
                            navController.navigate(MainActivity.ScreenData.Main.route)
                            doGetUserDetails()
                        },
                        modifier = Modifier
                            .offset(20.dp, 0.dp)
                            .size(80.dp, 50.dp)
                            .clip(RoundedCornerShape(13.dp)),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                    ) {
                        Text(text = "Delete", fontSize = 14.sp, color = Color.Black, textAlign = TextAlign.Center)
                        Icon(
                            painter = painterResource(id = R.drawable.deleteicon),
                            contentDescription = "Delete",
                            tint = Color.Red,
                            modifier = Modifier.size(100.dp)
                        )
                    }
                }
            }
        }
    }
}
