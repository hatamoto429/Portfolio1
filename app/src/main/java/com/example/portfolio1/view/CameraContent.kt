package com.example.portfolio1.view

import android.opengl.GLSurfaceView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.portfolio1.view.arview.ARSurfaceView
import com.example.portfolio1.viewModel.CameraViewModel
import com.example.portfolio1.viewModel.DetailViewModel


@Composable
fun CameraContent(navController: NavController, cameraViewModel: CameraViewModel) {

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
}