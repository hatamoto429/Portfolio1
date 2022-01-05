package com.example.portfolio1.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.portfolio1.viewModel.SettingsViewModel


@Composable
fun SettingsContent(navController: NavController, settingsViewModel: SettingsViewModel) {
    var generationAmount: Int?
fun SetCurrentInput(value: Int?){
    generationAmount = value
}
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        var title = ("Settings View")
        var deleteButtonText = ("Delete All Users")
        var createButtonText = ("Generate All Users")
        val textState = remember { mutableStateOf(TextFieldValue()) }

        var currentInput = ""

        Text(
            text = title,
            modifier = Modifier.padding(10.dp)
        )


        TextField(value = textState.value, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
            textState.value = it
            generationAmount = it.toString().toIntOrNull()
            println(generationAmount)
        })

        Button(

            onClick = {},
            modifier = Modifier.padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)


        ) {
            Text(text = deleteButtonText)

        }

        Button(

            onClick = {},
            modifier = Modifier.padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)


        ) {
            //Text(text = buttonText)

        }
    }

}