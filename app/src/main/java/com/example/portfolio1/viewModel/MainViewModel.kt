package com.example.portfolio1.viewModel

import android.app.Application
import android.text.Layout
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.*
import androidx.navigation.NavController
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

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val internalUser = MutableLiveData("")
    private val userdata = MutableLiveData<Welcome>(null)
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
        fun DisplayUser(welcome: Welcome?, navController: NavController, userViewModel: UserViewModel) {
        welcome?.results?.forEach() {
            val user = User(it.login.sha256, it.name.title, it.name.first, it.name.last, it.picture.large, it.picture.medium, it.dob.date, it.phone)
            userViewModel.insertUserDetails(user)
            Button (
                onClick = {
                   // modifier = Modifier.background(Color.White, RectangleShape)
                    navController.navigate("userDetails")
                },

                modifier = Modifier
                    .background(Color.White, RectangleShape)
                    .size(450.dp, 50.dp)
                    .border(1.dp, Color.Black),
            ){
                //Text(text = "Bild",  modifier = Modifier.align(alignment = Alignment.CenterStart))
                Text(text = user.title)
                Text(text = user.userFirstname)
                Text(text = user.userLastname)
                Text(text = "|", modifier = Modifier.offset(15.dp, 0.dp))
                Text(text = user.userTelephone, modifier = Modifier.offset(30.dp, 0.dp))
            }
        }
        //ImageView(

        //Picasso.get().load(user.picture.medium).into(imageView)
        //)
    }

}
