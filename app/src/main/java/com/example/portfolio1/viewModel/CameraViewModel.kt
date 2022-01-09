package com.example.portfolio1.viewModel

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.budiyev.android.codescanner.*
import com.example.portfolio1.MainActivity
import com.example.portfolio1.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

public const val CAMERA_REQUEST_CODE = 101

@HiltViewModel
class CameraViewModel @Inject constructor(
    application: Application,
    private val userRepo: UserRepo
) : AndroidViewModel(application) {

    fun getSingleUser(
        shaKey: String,
        navController: NavController,
        detailViewModel: DetailViewModel
    ) {
        viewModelScope.launch() {

            detailViewModel._currentUser.value = userRepo.getSingleUser(shaKey).first()
            navController.navigate(MainActivity.ScreenData.Detail.route)
        }
    }

    @Composable
    public fun codeScanner(navController: NavController, detailViewModel: DetailViewModel) {

        AndroidView(factory = { context ->
            CodeScannerView(context).apply {

                CodeScanner(context, this).apply {
                    camera = CodeScanner.CAMERA_BACK
                    formats = CodeScanner.ALL_FORMATS
                    autoFocusMode = AutoFocusMode.SAFE
                    scanMode = ScanMode.CONTINUOUS
                    isAutoFocusEnabled = true
                    isFlashEnabled = false
                    this.startPreview()
                    decodeCallback = DecodeCallback {
                        var shaKey = it.text
                        getSingleUser(shaKey, navController, detailViewModel)

                        //

                    }
                    errorCallback = ErrorCallback {
                    }
                }
            }
        })
    }
}

