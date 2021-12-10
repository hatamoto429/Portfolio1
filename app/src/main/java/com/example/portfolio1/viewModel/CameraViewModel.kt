package com.example.portfolio1.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavController

class CameraViewModel(application: Application) : AndroidViewModel(application)
{

    fun CameraContent(navController: NavController) {

        println("camera content")

    }


}
