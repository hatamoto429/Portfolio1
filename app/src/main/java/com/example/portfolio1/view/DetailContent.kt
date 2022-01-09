package com.example.portfolio1.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.portfolio1.database.entities.User
import com.example.portfolio1.viewModel.DetailViewModel

@Composable
fun DetailContent (navController: NavController, detailViewModel: DetailViewModel, user: User) {

    Column(

        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text(
            text = "User details:",
            modifier = Modifier
                .background(Color.Yellow)
                .padding(5.dp)
                .fillMaxWidth(1f)
                .align(Alignment.CenterHorizontally),
            fontSize = 15.sp
        )

        detailViewModel.displayInformation(user)

    }

}