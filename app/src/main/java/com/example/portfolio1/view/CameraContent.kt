package com.example.portfolio1.view

import android.Manifest
import android.content.pm.PackageManager
import android.opengl.GLSurfaceView
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.portfolio1.view.arview.ARSurfaceView
import com.example.portfolio1.viewModel.CameraViewModel
import com.example.portfolio1.viewModel.DetailViewModel


@Composable
fun CameraContent(navController: NavController, cameraViewModel: CameraViewModel) {

    PermissionQRCamera {
        cameraViewModel.codeScanner()
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