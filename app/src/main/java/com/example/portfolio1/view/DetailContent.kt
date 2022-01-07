package com.example.portfolio1.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.portfolio1.database.entities.User
import com.example.portfolio1.viewModel.DetailViewModel

//@ExperimentalAnimationApi
@Composable
fun DetailContent (navController: NavController, detailViewModel: DetailViewModel, user: User) {

    Column(

        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text (
            text = "ALL USER DETAILS",
            modifier = Modifier.padding(10.dp)
        )

        detailViewModel.displayInformation(user)


    }




}