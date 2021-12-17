package com.example.portfolio1.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.portfolio1.viewModel.MainViewModel
import com.example.portfolio1.viewModel.SettingsViewModel

@Composable
fun SettingsContent(navController: NavController, settingsViewModel: SettingsViewModel) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        var title = ("Settings View");
        var text = ("testButton");

        Text(
            text = title,
            modifier = Modifier.padding(10.dp)
        )

        Button(

            onClick = {},
            modifier = Modifier.padding(top = 8.dp),
        ) {
            text = text
        }
    }

}