package com.example.portfolio1.view.arview

import android.content.ContentValues.TAG
import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.util.Log
import android.util.Pair
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.example.portfolio1.AR.augmentedimage.AugmentedImageActivity
import com.example.portfolio1.AR.augmentedimage.rendering.AugmentedImageRenderer
import com.example.portfolio1.AR.common.helpers.DisplayRotationHelper
import com.example.portfolio1.AR.common.helpers.SnackbarHelper
import com.example.portfolio1.AR.common.helpers.TrackingStateHelper
import com.example.portfolio1.AR.common.rendering.BackgroundRenderer
import com.google.ar.core.*
import java.io.IOException
import java.util.HashMap
import java.util.*


import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/*
class ARSurfaceView(context: Context?) : GLSurfaceView(context),GLSurfaceView.Renderer {


    // Rendering. The Renderers are created here, and initialized when the GL surface is created.
    private val surfaceView: GLSurfaceView? = null
    private val fitToScanView: ImageView? = null
    private val glideRequestManager: RequestManager? = null

    private val session: Session? = null
    private val messageSnackbarHelper = SnackbarHelper()
    private val displayRotationHelper: DisplayRotationHelper? = null
    private val trackingStateHelper = TrackingStateHelper(this)

    private val backgroundRenderer = BackgroundRenderer()
    private val augmentedImageRenderer = AugmentedImageRenderer()

    private val shouldConfigureSession = false

    // Augmented image configuration and rendering.
    // Load a single image (true) or a pre-generated image database (false).
    private val useSingleImage = false

    // Augmented image and its associated center pose anchor, keyed by index of the augmented image in
    // the
    // database.
    private val augmentedImageMap: HashMap<Int, Pair<AugmentedImage, Anchor>> =
        HashMap<Int, Pair<AugmentedImage, Anchor>>()

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0.1f, 0.1f, 0.1f, 1.0f)

        // Prepare the rendering objects. This involves reading shaders, so may throw an IOException.

        // Prepare the rendering objects. This involves reading shaders, so may throw an IOException.
        try {
            // Create the texture and pass it to ARCore session to be filled during update().
            backgroundRenderer.createOnGlThread( /*context=*/this)
            augmentedImageRenderer.createOnGlThread( /*context=*/this)
        } catch (e: IOException) {
            Log.e(AugmentedImageActivity.TAG, "Failed to read an asset file", e)
        }
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {

/*
        displayRotationHelper.onSurfaceChanged(width, height)
        GLES20.glViewport(0, 0, width, height)
*/
    }

    override fun onDrawFrame(gl: GL10?) {
        // Clear screen to notify driver it should not load any pixels from previous frame.

        // Clear screen to notify driver it should not load any pixels from previous frame.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)

        if (session == null) {
            return
        }
        // Notify ARCore session that the view size changed so that the perspective matrix and
        // the video background can be properly adjusted.
        // Notify ARCore session that the view size changed so that the perspective matrix and
        // the video background can be properly adjusted.
        displayRotationHelper.updateSessionIfNeeded(session)

        try {
            session.setCameraTextureName(backgroundRenderer.textureId)

            // Obtain the current frame from ARSession. When the configuration is set to
            // UpdateMode.BLOCKING (it is by default), this will throttle the rendering to the
            // camera framerate.
            val frame: Frame = session.update()
            val camera = frame.camera

            // Keep the screen unlocked while tracking, but allow it to lock when tracking stops.
            trackingStateHelper.updateKeepScreenOnFlag(camera.trackingState)

            // If frame is ready, render camera preview image to the GL surface.
            backgroundRenderer.draw(frame)

            // Get projection matrix.
            val projmtx = FloatArray(16)
            camera.getProjectionMatrix(projmtx, 0, 0.1f, 100.0f)

            // Get camera matrix and draw.
            val viewmtx = FloatArray(16)
            camera.getViewMatrix(viewmtx, 0)

            // Compute lighting from average intensity of the image.
            val colorCorrectionRgba = FloatArray(4)
            frame.lightEstimate.getColorCorrection(colorCorrectionRgba, 0)

            // Visualize augmented images.
            drawAugmentedImages(frame, projmtx, viewmtx, colorCorrectionRgba)
        } catch (t: Throwable) {
            // Avoid crashing the application due to unhandled exceptions.
            // Log.e(AugmentedImageActivity.TAG, "Exception on the OpenGL thread", t)
        }
    }

    private fun drawAugmentedImages(
        frame: Frame, projmtx: FloatArray, viewmtx: FloatArray, colorCorrectionRgba: FloatArray
    ) {
        val updatedAugmentedImages = frame.getUpdatedTrackables(
            AugmentedImage::class.java
        )

        // Iterate to update augmentedImageMap, remove elements we cannot draw.
        for (augmentedImage in updatedAugmentedImages) {
            when (augmentedImage.trackingState) {
                TrackingState.PAUSED -> {
                    // When an image is in PAUSED state, but the camera is not PAUSED, it has been detected,
                    // but not yet tracked.
                    val text = String.format("Detected Image %d", augmentedImage.index)
                    messageSnackbarHelper.showMessage(this, text)
                }
                TrackingState.TRACKING -> {

                    // Have to switch to UI Thread to update View.
                   // Runnable { fitToScanView.setVisibility(GONE) }

                    // Create a new anchor for newly found images.
                    if (!augmentedImageMap.containsKey(augmentedImage.index)) {
                        val centerPoseAnchor =
                            augmentedImage.createAnchor(augmentedImage.centerPose)
                        augmentedImageMap.put(
                            augmentedImage.index, Pair.create(augmentedImage, centerPoseAnchor)
                        )
                    }
                }
                TrackingState.STOPPED -> augmentedImageMap.remove(augmentedImage.index)
                else -> {
                }
            }
        }

        // Draw all images in augmentedImageMap
        for (pair in augmentedImageMap.values) {
            val augmentedImage = pair.first
            val centerAnchor: Anchor = augmentedImageMap.get(augmentedImage.index).second
            when (augmentedImage.trackingState) {
                TrackingState.TRACKING -> augmentedImageRenderer.draw(
                    viewmtx, projmtx, augmentedImage, centerAnchor, colorCorrectionRgba
                )
                else -> {
                }
            }
        }
    }

}
        */