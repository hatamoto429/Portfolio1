package com.example.portfolio1.view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.ScanMode
import com.example.portfolio1.viewModel.DetailViewModel
import com.example.portfolio1.viewModel.MainViewModel

//@ExperimentalAnimationApi
@Composable
fun DetailContent (navController: NavController, detailViewModel: DetailViewModel) {

    Column(

        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text (
            text = "ALL USER DETAILS",
            modifier = Modifier.padding(10.dp)
        )

        detailViewModel.displayInformation()


    }




}