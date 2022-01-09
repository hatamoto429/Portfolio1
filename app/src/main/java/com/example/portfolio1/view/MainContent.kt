package com.example.portfolio1.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.portfolio1.viewModel.DetailViewModel
import com.example.portfolio1.viewModel.MainViewModel

@Composable
fun MainContent(navController: NavController, mainViewModel: MainViewModel, detailViewModel: DetailViewModel) {


    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var userCount = 10;
        val userData by  mainViewModel.allUser.observeAsState("")
        val userWelcome by mainViewModel.welcomeData.observeAsState(null)
        val usersInDatabase by mainViewModel.userDetails.collectAsState(null)
        var isLoading by remember { mutableStateOf(false) }

        Text(
            text = "List of users",
            modifier = Modifier
                .background(Color.LightGray)
                .padding(10.dp)
                .fillMaxWidth(1f)
                .align(Alignment.CenterHorizontally),
            fontSize = 25.sp
        )

        Text(
            text = userData,
            modifier = Modifier.padding(5.dp),
        )

       mainViewModel.DisplayUser(userWelcome, navController, detailViewModel)

        if (isLoading) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(Modifier.size(30.dp))
            }
        }

        if (userData.isNotEmpty()) {
            FullScreenDialog(text = userData)
            {
                mainViewModel.resetAllUser()
            }
            isLoading = false
        }
    }
}

@Composable
fun FullScreenDialog(text: String, onClose: () -> Unit) {
    Text(text = "All User:")
}


