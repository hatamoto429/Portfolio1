package com.example.portfolio1.viewModel

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavController

//https://github.com/google-ar/arcore-android-sdk/tree/master/samples example- wichtig : Augmented Image
// AR core basis, dann programm drauf - läuft mit open gl
//Augmented Image Activity - genau das was wir brauchen

class CameraViewModel(application: Application) : AndroidViewModel(application)
{

    @Composable
    fun CameraContent(navController: NavController) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally

        ){
            var title = ("Camera View");

            Text(
                text = title,
                modifier = Modifier.padding(10.dp)
            )


        }



    }


}
