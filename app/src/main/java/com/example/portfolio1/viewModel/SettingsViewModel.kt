package com.example.portfolio1.viewModel

import android.app.Application
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.portfolio1.database.entities.User
import com.example.portfolio1.repository.UserRepo
import com.example.portfolio1.webAPI.ktorHttpClient
import com.example.portfolio1.webAPI.randomUserAPI
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import javax.inject.Inject




@HiltViewModel
class SettingsViewModel @Inject constructor(private val userRepo: UserRepo, application: Application) : AndroidViewModel(application) {
    val currentGeneratedUserCount = MutableLiveData<Int?>(10)
    val userGenCount: LiveData<Int?> = currentGeneratedUserCount
    private val api = randomUserAPI(ktorHttpClient)
    private val _response = MutableLiveData<Long>()
    val response: LiveData<Long> = _response


    //insert user details to room database
    private fun insertUserDetails(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            _response.postValue(userRepo.createUserRecords(user))
        }
    }

    fun deleteAllUsers(){
        viewModelScope.launch(Dispatchers.IO){
            userRepo.deleteAllUsers()
        }
    }
    private fun generateQRCode(text:String) : Bitmap {
        val width = 500;
        val height = 500;
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val codeWriter = MultiFormatWriter()
        try{
            val bitMatrix = codeWriter.encode(text, BarcodeFormat.QR_CODE, width, height)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x,y, if (bitMatrix[x,y] ) Color.Black.hashCode() else Color.White.hashCode())
                }
            }
        }
        catch (e: WriterException){
            Log.d("QRGenerator", "QRGenerator: ${e.message}")
        }
        return bitmap
    }

    fun getPath(context: Context, uri: Uri): String? {
        val isKitKatorAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

        // DocumentProvider
        if (isKitKatorAbove && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }

            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id))
                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])
                return getDataColumn(context, contentUri, selection, selectionArgs)
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            return getDataColumn(context, uri, null, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    fun getDataColumn(context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)
        try {
            cursor = context.getContentResolver().query(uri!!, projection, selection, selectionArgs,null)
            if (cursor != null && cursor.moveToFirst()) {
                val column_index: Int = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(column_index)
            }
        } finally {
            if (cursor != null) cursor.close()
        }
        return null
    }

    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }


    fun StoreQR(QRGenerationName: String, ImageTitle: String, context: Context){
        var bitmap = generateQRCode(QRGenerationName)
        //val out = ByteArrayOutputStream()
        //saveImageToInternalStorage(bitmap, context)
        //var image = bitmap.compress(Bitmap.CompressFormat.PNG, 75, out)
        MediaStore.Images.Media.insertImage(context.contentResolver, bitmap ,ImageTitle , "QR code for Portfolio App")
    }

    // Method to save an image to internal storage
    private fun saveImageToInternalStorage(bitmap: Bitmap, context: Context): Uri {

        // Create a file to save the image
        //var path = getRealPathFromURI(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        //val wrapper = ContextWrapper(context)
        val DOWNLOAD_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        //var file = wrapper.getDir("images", Context.MODE_PRIVATE)
        //var file = wrapper.getDir("images", Context.M)
        var file = File(DOWNLOAD_DIR.absolutePath + "/test.png")

        //file = File(file, "${UUID.randomUUID()}.jpg")

        try {

            // Get the file output stream
            val stream: OutputStream = FileOutputStream(file)

            // Compress bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            // Flush the stream
            stream.flush()

            // Close stream
            stream.close()
        } catch (e: IOException){ // Catch the exception
            e.printStackTrace()
        }

        // Return the saved image uri
        return Uri.parse(file.absolutePath)
    }


    fun fillDatabaseWithUsers(count: Int, shouldGenerateQR: Boolean, context: Context){
        viewModelScope.launch {
            var users = api.get(count)
            users?.results?.forEach() {
                var user = User(
                    it.login.sha256,
                    it.name.title,
                    it.name.first,
                    it.name.last,
                    it.picture.large,
                    it.picture.medium,
                    it.dob.date,
                    it.phone
                )
                var newDate = it.dob.date.subSequence(0, 10)
                user.userBirthday = newDate.toString()
                insertUserDetails(user)

                if (shouldGenerateQR){
                    StoreQR(user.sha256, user.userFirstname + " " + user.userLastname, context)
                }
            }
        }
    }
}