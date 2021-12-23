package com.example.portfolio1.database.repositories

import com.example.portfolio1.database.entities.ApiUser
import com.example.portfolio1.database.entities.Name
import com.example.portfolio1.database.entities.Result
import com.example.portfolio1.viewModel.User
import com.example.portfolio1.webAPI.NetworkSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository (

    private val randomUserApi: NetworkSource
    ) {

        fun getUsers(resultListCount: Int ): Flow<List<User>> = flow {
            val userResultDTO: ApiUser = randomUserApi.getUsers(resultListCount)
            val peopleList = mutableListOf<User>()
            userResultDTO.results?.forEach {
                peopleList.add(
                    it.toUser()
                )
            }
            emit(peopleList)
        }

         private fun Result.toUser(): User {
             return User(
                 userFirstname = this.name?.first?:"",
                 userLastname = this.name?.last?:"",
                 userImage = this.picture?.medium?:"",
                 userBirthday = this.dob?.date?:"",
                 userTelephone = this.phone?:"",
             )
         }



        // unnötig?
        private fun Name.getFullName(): String  {
            return "$first $last"
        }


    }