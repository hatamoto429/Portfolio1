package com.example.portfolio1.viewModel

import android.app.Application
import android.graphics.drawable.Icon
import android.text.Layout
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.*
import androidx.navigation.NavController
import com.example.portfolio1.R
import com.example.portfolio1.database.entities.User
import com.example.portfolio1.database.entities.Welcome
import com.example.portfolio1.preferences.PreferenceStorage
import com.example.portfolio1.repository.UserRepo
import com.example.portfolio1.webAPI.ktorHttpClient
import com.example.portfolio1.webAPI.randomUserAPI
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userRepo: UserRepo, application: Application) : AndroidViewModel(application) {

    private val internalUser = MutableLiveData("")
    private val userdata = MutableLiveData<Welcome>(null)
    val welcomeData: LiveData<Welcome> = userdata
    val allUser: LiveData<String> = internalUser
    private val api = randomUserAPI(ktorHttpClient, 10)
    private val _userDetails = MutableStateFlow<List<User>>(emptyList())
    val userDetails : StateFlow<List<User>> =  _userDetails
    private val _response = MutableLiveData<Long>()
    val response: LiveData<Long> = _response

    //insert user details to room database
    fun insertUserDetails(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            _response.postValue(userRepo.createUserRecords(user))
        }
    }

    fun doDeleteSingleUserRecord(shaKey :String){
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.deleteSingleUserRecord(shaKey)
        }
    }

    fun doGetUserDetails(){
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.getUsers
                .catch { e->
                    //Log error here
                }
                .collect {
                    _userDetails.value = it
                }
        }
    }

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

    fun fillDatabaseWithUsers(welcome: Welcome?){
        welcome?.results?.forEach() {
            val user = User(
                it.login.sha256,
                it.name.title,
                it.name.first,
                it.name.last,
                it.picture.large,
                it.picture.medium,
                it.dob.date,
                it.phone
            )
            insertUserDetails(user)
        }
    }

    @Composable
        fun DisplayUser(welcome: Welcome?, navController: NavController) {
        fillDatabaseWithUsers(welcome)
        doGetUserDetails()
        userDetails.value.forEach() {
            Button (
                onClick = {
                   // modifier = Modifier.background(Color.White, RectangleShape)
                    navController.navigate("userDetails")
                },

                modifier = Modifier
                    .size(450.dp, 50.dp)
                    .border(1.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
            ){
                Text(text = it.title)
                Text(text = it.userFirstname)
                Text(text = it.userLastname)
                Text(text = "|", modifier = Modifier.offset(15.dp, 0.dp))
                Text(text = it.userTelephone, modifier = Modifier.offset(30.dp, 0.dp))
                Button(
                    onClick = {
                        doDeleteSingleUserRecord(it.sha256)
                },
                    modifier = Modifier
                        .size(50.dp, 20.dp)
                        .border(1.dp, Color.Black),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.deleteicon),
                        contentDescription = "Delete",
                        tint = Color.Red,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
        //ImageView(

        //Picasso.get().load(user.picture.medium).into(imageView)
        //)
    }

}
