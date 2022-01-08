package com.example.portfolio1.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.portfolio1.MainActivity
import com.example.portfolio1.viewModel.CameraViewModel
import com.example.portfolio1.viewModel.DetailViewModel

val _hasBeenFound = MutableLiveData(false)
val hasBeenFound: LiveData<Boolean> = _hasBeenFound

@Composable
public fun SwitchToView(navController: NavController){
    if (_hasBeenFound.value!!){
        navController.navigate(MainActivity.ScreenData.Detail.route)
    }
}

@Composable
fun CameraContent(navController: NavController, context: Context, cameraViewModel: CameraViewModel, detailViewModel: DetailViewModel) {

    PermissionQRCamera {
        cameraViewModel.codeScanner(navController, detailViewModel)
    }

    /*
    AndroidView(factory = {context ->
        ARSurfaceView(context).apply  {

            setPreserveEGLContextOnPause(true)
            setEGLContextClientVersion(2)
            setEGLConfigChooser(8, 8, 8, 8, 16, 0) // Alpha used for plane blending.

            setRenderer(this)
            setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY)
            setWillNotDraw(false)
        }
    })
    */

}

@Composable
fun PermissionQRCamera(onGranted: @Composable () -> Unit) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted: Do something
            Log.d("ExampleScreen", "PERMISSION GRANTED")

        } else {
            // Permission Denied: Do something
            Log.d("ExampleScreen", "PERMISSION DENIED")
        }
    }

    val context = LocalContext.current

    when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) -> {
            // Some works that require permission
            onGranted()
        }
        else -> {
            // Asking for permission
            SideEffect {
                launcher.launch(Manifest.permission.CAMERA)
            }
        }
    }
}