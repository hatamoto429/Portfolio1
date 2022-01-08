package com.example.portfolio1.view

import android.content.Context
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.portfolio1.viewModel.SettingsViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import java.io.ByteArrayOutputStream


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

@Composable
fun DisplayQR(navController: NavController, settingsViewModel: SettingsViewModel, context: Context){
    var bitmap = generateQRCode("test");
    val out = ByteArrayOutputStream()
    var image = bitmap.compress(Bitmap.CompressFormat.PNG, 75, out);
    MediaStore.Images.Media.insertImage(context.contentResolver, bitmap ,"QR-Code" , "description");

  /*  Image(
        painterResource(),
        modifier = Modifier
            .background(Color.Cyan)
            .padding(20.dp),
        contentDescription = "qr image",

        )*/
}

@Composable
fun SettingsContent(navController: NavController, settingsViewModel: SettingsViewModel) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        var deleteButtonText = ("Delete All Users")
        var createButtonText = ("Generate All Users")
        val textState = remember { mutableStateOf(TextFieldValue()) }
        var generationAmount: Int?
        var currentInput = ""
        //val userGenCount by settingsViewModel.userGenCount.observeAsState()
        var userGenCount by remember { mutableStateOf(10) }

        Text(
            text = "Settings:",
            modifier = Modifier
                .background(Color.Yellow)
                .padding(5.dp)
                .fillMaxWidth(1f)
                .align(Alignment.CenterHorizontally),
            fontSize = 15.sp
        )

        Text(
            text = "Current amount of generated users: $userGenCount",
            modifier = Modifier.padding(10.dp)
        )
        Text(
            text = "Input the new number of users to generate:" ,
            modifier = Modifier.padding(10.dp)
        )

        TextField(value = textState.value, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
            textState.value = it
                currentInput = textState.value.text
                generationAmount = currentInput.toIntOrNull()
                if (generationAmount != null){
                    userGenCount = generationAmount as Int
                }

        })
        Button(

            onClick = {
                settingsViewModel.fillDatabaseWithUsers(userGenCount)
            },
            modifier = Modifier.padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)


        ) {
            val buttonText = "Generate new users"
            Text(text = buttonText)

        }
        Button(

            onClick = {
                      settingsViewModel.deleteAllUsers()
            },
            modifier = Modifier.padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)


        ) {
            Text(text = deleteButtonText)

        }


    }

}