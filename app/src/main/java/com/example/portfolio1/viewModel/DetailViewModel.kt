package com.example.portfolio1.viewModel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.*
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
import com.example.portfolio1.utility.QRGenerator


class DetailViewModel(application: Application) : AndroidViewModel(application) {

    val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> = _currentUser

    @Composable
    fun loadSelectedUser(user: User) {
        Text(
            text = "user details",
            modifier = Modifier.padding(10.dp)
        )
        //displayInformation(user)
    }

    @Composable
    fun displayInformation(user: User, context: Context) {
        Column(
            modifier = Modifier
                //.background(Color.LightGray)
                //.size(400.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //var _currentContext = LocalContext.current
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
                        .background(Color.LightGray)
                        .padding(15.dp),
                    text = _currentUser.value?.title.toString(),
                    fontSize = 15.sp
                )

                Text(
                    modifier = Modifier
                        .background(Color.LightGray)
                        .padding(15.dp),
                    text = _currentUser.value?.userFirstname.toString(),
                    fontSize = 15.sp
                )

                Text(
                    modifier = Modifier
                        .background(Color.LightGray)
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

                    var generator = QRGenerator()

                    generator.StoreQR(
                        _currentUser.value!!.sha256,
                        _currentUser.value!!.userFirstname + " " + _currentUser.value!!.userLastname,
                        context
                    )

                    var toast = Toast(context)
                    toast.setText("QR has been saved to the device")
                    toast.show()
                }
            ) {
                Text(text = "Download user QR code")

            }
        }
    }
}

