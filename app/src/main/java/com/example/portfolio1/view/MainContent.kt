package com.example.portfolio1.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.portfolio1.viewModel.MainViewModel

@Composable
fun MainContent(navController: NavController, mainViewModel: MainViewModel) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var userCount = 10;
        var isLoading by remember { mutableStateOf(false) }
        val userData by  mainViewModel.allUser.observeAsState("")

        Text(
            text = "All User:",
            modifier = Modifier.padding(10.dp)
        )

        Text(
            text = userData,
            modifier = Modifier.padding(8.dp),
        )

        Button(

            onClick = {
                try {
                    mainViewModel.loadAllUser(userCount.toInt())
                    //mainViewModel.DisplayUser()
                    isLoading = true;
                } catch (exception: NumberFormatException) {

                    val errorMessage = "Error, no user found! Try again!"
                    val errorDuration = Toast.LENGTH_SHORT
                    /*
                    val toast = Toast.makeText(applicationContext, errorMessage, errorDuration)
                    toast.show()
                    */
                }
            },
            modifier = Modifier.padding(top = 8.dp),
        ) {
            Text(text = "Load")
        }

        /*
        Button(
            onClick = { },
            modifier = Modifier.padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
        ) {
            Text(text = "Delete")
        }
        */

        if (isLoading) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(Modifier.size(48.dp))
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


