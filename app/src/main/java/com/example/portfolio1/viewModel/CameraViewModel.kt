package com.example.portfolio1.viewModel

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.room.Query
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.portfolio1.MainActivity
import com.example.portfolio1.database.daos.UserDao
import com.example.portfolio1.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.synthetic.main.qr_scanner.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

public const val CAMERA_REQUEST_CODE = 101

@HiltViewModel
class CameraViewModel @Inject constructor(application: Application, private val userRepo: UserRepo) : AndroidViewModel(application)
{

    fun getSingleUser(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.getSingleUser(id)
        }
    }

    @Composable
    public fun codeScanner(context: Context, navController: NavController) {

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
                        getSingleUser(shaKey)

                        navController.navigate("userDetails")
                    }
                    errorCallback = ErrorCallback {
                    }
                }
            }
        })
    }



    /*
    @Composable
    fun CameraContent(navController: NavController) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally

        ){
            var title = ("Camera View");

            Text(
                text = title,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
    */
}
