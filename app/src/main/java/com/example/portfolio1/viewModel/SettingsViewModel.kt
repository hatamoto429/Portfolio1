package com.example.portfolio1.viewModel

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
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
import java.io.ByteArrayOutputStream
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

    fun StoreQR(QRGenerationName: String, ImageTitle: String, context: Context){
        var bitmap = generateQRCode(QRGenerationName);
        val out = ByteArrayOutputStream()
        var image = bitmap.compress(Bitmap.CompressFormat.PNG, 75, out);
        MediaStore.Images.Media.insertImage(context.contentResolver, bitmap ,ImageTitle , "QR code for Portfolio App");
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