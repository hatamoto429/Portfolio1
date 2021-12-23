package com.example.portfolio1
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.portfolio1.ui.theme.Portfolio1Theme
import com.example.portfolio1.AR.augmentedimage.rendering.AugmentedImageRenderer
import com.example.portfolio1.databinding.ActivityMainBinding
import com.example.portfolio1.view.CameraContent
import com.example.portfolio1.view.MainContent
import com.example.portfolio1.view.SettingsContent
import com.example.portfolio1.viewModel.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

/*
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
 */

class MainActivity : ComponentActivity() {

    private lateinit var userListAdapter: UserListAdapter

    private val mainViewModel: MainViewModel by viewModels()
    private val mainViewModelTest: MainViewModelTest by viewModels()
    private val detailViewModel: DetailViewModel by viewModels()
    private val cameraViewModel: CameraViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()
   // private val arViewModel: AugmentedImageRenderer by viewModels()

    sealed class ScreenData(
        val route: String,
        @StringRes val resourceId: Int,
        val icon: ImageVector
    ) {
        object Main : ScreenData("MainUser", R.string.main, Icons.Filled.AccountCircle)
        object Camera : ScreenData("Camera", R.string.camera, Icons.Filled.Search)
        object Settings : ScreenData("Settings", R.string.settings, Icons.Filled.Settings)
    }

    /*
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
  */

    private fun initViews(binding: ActivityMainBinding) {

        userListAdapter = UserListAdapter()

        binding.peopleListRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userListAdapter
        }
    }

    private fun subscribeObservers() {
        mainViewModelTest.loadAllUser(25)
        lifecycleScope.launch {
            mainViewModelTest
                .userListFlow
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    /*
                .collect {
                    userListAdapter.submitList(it)
                }
                */

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews(binding)
        subscribeObservers()

        setContent {
            val navController = rememberNavController()

            Portfolio1Theme {
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(bottomBar = {
                        BottomNavigation {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            listOf(ScreenData.Main, ScreenData.Camera, ScreenData.Settings).forEach { item ->
                                BottomNavigationItem(
                                    icon = { Icon(item.icon, contentDescription = null) },
                                    label = { Text(text = stringResource(id = item.resourceId)) },
                                    selected = currentDestination?.hierarchy?.any {
                                        it.route == item.route
                                    } == true,
                                    onClick = {
                                        navController.navigate(item.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                    ) {innerPadding ->
                        NavHost(
                            navController, startDestination = ScreenData.Main.route,
                            Modifier.padding(innerPadding)
                        ) {
                            composable(ScreenData.Main.route) {
                                MainContent(navController, mainViewModel)
                            }
                            composable(ScreenData.Camera.route) {
                                CameraContent(navController, cameraViewModel)
                            }
                            composable(ScreenData.Settings.route) {
                                SettingsContent(navController, settingsViewModel)
                            }

                        }
                    }
                }
            }
        }
    }
}


