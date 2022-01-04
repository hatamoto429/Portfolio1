package com.example.portfolio1.viewModel

import android.app.Application
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavController
import com.example.portfolio1.database.entities.Welcome
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Animatable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.portfolio1.R


class DetailViewModel (application: Application) : AndroidViewModel(application)
{

   //@ExperimentalAnimationApi
   @Composable
   fun loadSelectedUser(welcome: Welcome?, modifier: Modifier = Modifier) {

       Text(
           text = "user details",
           modifier = Modifier.padding(10.dp)
       )

       displayInformation()

   }

       //@ExperimentalAnimationApi
       @Composable
       fun displayInformation() {

           Row(
               horizontalArrangement = Arrangement.Start,
               verticalAlignment = Alignment.CenterVertically
           ) {

               Card(modifier = Modifier.size(150.dp),
                    shape = CircleShape)
               {
                   Image(painterResource (R.drawable.userimage), contentDescription = "user image")
               }


               Text(
                   modifier = Modifier
                       .background(Color.Yellow)
                       .padding(15.dp),
                   text = "Title",
                   fontSize = 15.sp
               )

               Text(
                   modifier = Modifier
                       .background(Color.Yellow)
                       .padding(15.dp),
                   text = "Firstname",
                   fontSize = 15.sp
               )

               Text(
                   modifier = Modifier
                       .background(Color.Yellow)
                       .padding(15.dp),
                   text = "Lastname",
                   fontSize = 15.sp
               )
           }

           Column(
               verticalArrangement = Arrangement.Center,
               horizontalAlignment = Alignment.Start,
               modifier = Modifier.padding(15.dp)
           ) {


            /*
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically {
                    with(density) { -40.dp.roundToPx() }
                } +expandVertically(
                    expandFrom = Alignment.Top
                ) + fadeIn(
                    initialAlpha = 0.3f
                ),
                exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {
                Text(text = "hello there",
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp))
            }
            */


               Text(
                   modifier = Modifier
                       .background(Color.LightGray)
                       .padding(15.dp),
                   text = "Birthdate:"
               )

               Text(
                   modifier = Modifier
                       .background(Color.LightGray)
                       .padding(15.dp),
                   text = "Phone:"
               )

           }

           Card(
               modifier = Modifier.size(250.dp),
               elevation = 30.dp,
               shape = RectangleShape)
           {

              // Spacer(modifier = Modifier.size(10.dp))

               Image(
                   painterResource(R.drawable.qrexample),
                   modifier = Modifier
                       .background(Color.Cyan)
                       .padding(20.dp),
                   contentDescription = "qr image",

               )
           }

       }

}
