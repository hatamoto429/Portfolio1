package com.example.portfolio1.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.portfolio1.database.entities.User
import com.example.portfolio1.database.entities.Welcome
import com.example.portfolio1.repository.UserRepo
import com.example.portfolio1.webAPI.ktorHttpClient
import com.example.portfolio1.webAPI.randomUserAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val userRepo: UserRepo, application: Application) : AndroidViewModel(application) {
    val currentGeneratedUserCount = MutableLiveData<Int?>(10)
    val userGenCount: LiveData<Int?> = currentGeneratedUserCount
    private val api = randomUserAPI(ktorHttpClient, 10)
    private val _response = MutableLiveData<Long>()
    val response: LiveData<Long> = _response

    //insert user details to room database
    private fun insertUserDetails(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            _response.postValue(userRepo.createUserRecords(user))
        }
    }

    fun deleteAllUsers(){
        viewModelScope.launch(Dispatchers.IO){
            userRepo.deleteAllUsers()
        }
    }

    fun fillDatabaseWithUsers(count: Int){
        viewModelScope.launch {
            val users = api.get(count)
            users?.results?.forEach() {
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
    }
}