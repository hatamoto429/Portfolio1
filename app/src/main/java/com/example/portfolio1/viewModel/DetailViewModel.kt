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

//DetailViewModel macht wenig sinn als extra seite


class DetailViewModel (application: Application) : AndroidViewModel(application)
{

    @Composable
    fun DetailContent(navController: NavController) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally

        ){
            var title = ("User Detail View");

            Text(
                text = title,
                modifier = Modifier.padding(10.dp)
            )


        }



    }

}
