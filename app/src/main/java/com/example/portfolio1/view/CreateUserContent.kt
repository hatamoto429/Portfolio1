package com.example.portfolio1.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.portfolio1.MainActivity
import com.example.portfolio1.database.entities.User
import com.example.portfolio1.viewModel.CreateUserViewModel
import com.example.portfolio1.viewModel.DetailViewModel

@Composable
fun CreateUserContent(navController: NavController, createUserViewModel: CreateUserViewModel) {

    val DOBTextState = remember { mutableStateOf(TextFieldValue()) }
    var DOBAmount: Int?
    var DOBCurrentInput = ""

    val PhoneNumberTextState = remember { mutableStateOf(TextFieldValue()) }
    var PhoneNumberAmount: Int?
    var PhoneNumberCurrentInput = ""

    var title by remember { mutableStateOf("") }
    var fName by remember { mutableStateOf("") }
    var lName by remember { mutableStateOf("") }
    var lPicture = "https://tse1.mm.bing.net/th?id=OIP.MbN2E_gnMsKH7bVXdHMX8AHaHa&pid=Api"
    var mPicture = "https://tse1.mm.bing.net/th?id=OIP.MbN2E_gnMsKH7bVXdHMX8AHaHa&pid=Api"
    var DOB by remember { mutableStateOf("") }
    var PhoneNumber by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Text(text = "Title")

        TextField(value = title,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
            onValueChange = {
                title = it
            })

        Text(text = "First name")

        TextField(value = fName,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
            onValueChange = {
                fName = it
            })

        Text(text = "Last name")

        TextField(value = lName,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
            onValueChange = {
                lName = it
            })

        Text(text = "Date of birth")

        TextField(value = DOB,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                DOB = it
            })

        Text(text = "Phone number")

        TextField(value = PhoneNumber,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                PhoneNumber = it
            })

        Button(
            onClick = {
                if(title != "" && fName != "" && lName != "" && DOB != "" && PhoneNumber != ""){
                    var user = User(createUserViewModel.generateSHAKey(256), title, fName, lName, mPicture, lPicture, DOB.toString(), PhoneNumber.toString())
                    createUserViewModel.insertUserDetails(user)
                    navController.navigate(MainActivity.ScreenData.Main.route)
                }
            },
            modifier = Modifier.padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)


        ) {
            val buttonText = "Create user"
            Text(text = buttonText)

        }
    }

}