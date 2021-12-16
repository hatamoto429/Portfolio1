package com.example.portfolio1.view

import android.opengl.GLSurfaceView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView


@Composable
fun CameraContent() {

    AndroidView(factory = {context ->
        GLSurfaceView(context).apply  {

            setPreserveEGLContextOnPause(true)
            setEGLContextClientVersion(2)
            setEGLConfigChooser(8, 8, 8, 8, 16, 0) // Alpha used for plane blending.

          //  setRenderer(this)
            setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY)
            setWillNotDraw(false)
        }
    })


}