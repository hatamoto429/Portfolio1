package com.example.portfolio1.viewModel

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coil.compose.rememberImagePainter
import com.example.portfolio1.database.entities.User


class DetailViewModel(application: Application) : AndroidViewModel(application) {

    val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> = _currentUser


    @Composable
    fun loadSelectedUser(user: User) {
        Text(
            text = "user details",
            modifier = Modifier.padding(10.dp)
        )
        displayInformation(user)
    }

    @Composable
    fun displayInformation(user: User) {

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Card(
                modifier = Modifier
                    .size(180.dp)
                    .padding(20.dp)
                    .clip(CircleShape)
                    .shadow(10.dp, clip = true)
            )
            {
                Image(
                    painter = rememberImagePainter(_currentUser.value?.pictureMedium),
                    contentDescription = null,
                    modifier = Modifier
                        .shadow(10.dp, shape = CircleShape)
                        .size(100.dp)
                        .clip(CircleShape)

                )
            }

            Text(
                modifier = Modifier
                    .background(Color.Yellow)
                    .padding(15.dp),
                text = _currentUser.value?.title.toString(),
                fontSize = 15.sp
            )

            Text(
                modifier = Modifier
                    .background(Color.Yellow)
                    .padding(15.dp),
                text = _currentUser.value?.userFirstname.toString(),
                fontSize = 15.sp
            )

            Text(
                modifier = Modifier
                    .background(Color.Yellow)
                    .padding(15.dp),
                text = _currentUser.value?.userLastname.toString(),
                fontSize = 15.sp
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(10.dp)
        ) {

            Text(
                modifier = Modifier
                    .background(Color.LightGray, shape = RoundedCornerShape(13.dp))
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(13.dp))
                    .padding(10.dp),
                text = "Birthdate:"
            )

            Text(
                modifier = Modifier
                    .background(Color.Transparent, shape = RoundedCornerShape(13.dp))
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(13.dp))
                    .padding(10.dp),
                text = _currentUser.value?.userBirthday.toString()
            )


            Text(
                modifier = Modifier
                    .background(Color.LightGray, shape = RoundedCornerShape(13.dp))
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(13.dp))
                    .padding(10.dp),
                text = "Telephone:"
            )

            Text(
                modifier = Modifier
                    .background(Color.Transparent, shape = RoundedCornerShape(13.dp))
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(13.dp))
                    .padding(10.dp),
                text = _currentUser.value?.userTelephone.toString()
            )
        }


        Button(
                       
            modifier = Modifier
                .background(Color.Blue, shape = RoundedCornerShape(13.dp))
                .padding(5.dp)
                .clip(shape = RoundedCornerShape(13.dp)),
            onClick = {

            }
        ){
            Text(text = "Download User QR code")
        }
    }
}

