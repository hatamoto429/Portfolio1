package com.example.portfolio1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.portfolio1.viewModel.MainViewModel
import com.example.portfolio1.ui.theme.Portfolio1Theme

import com.example.portfolio1.viewModel.CameraViewModel


class MainActivity : ComponentActivity() {

   // private val mainViewModel: MainViewModel by viewModels()
    private val cameraViewModel: CameraViewModel by viewModels()

    sealed class ScreenData(
        val route: String,
        @StringRes val resourceId: Int,
        val icon: ImageVector
    ) {
        object User : ScreenData("User", R.string.user, Icons.Filled.Face)
        object Camera : ScreenData("Camera", R.string.camera, Icons.Filled.Phone)
        object Settings : ScreenData("Settings", R.string.settings, Icons.Filled.Settings)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            Portfolio1Theme {
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(bottomBar = {
                        BottomNavigation {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            listOf(ScreenData.User, ScreenData.Camera, ScreenData.Settings).forEach { item ->
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
                            navController, startDestination = ScreenData.User.route,
                            Modifier.padding(innerPadding)
                        ) {
                            composable(ScreenData.User.route) {
                                UserContent(navController)
                            }
                            composable(ScreenData.Camera.route) {
                                cameraViewModel.CameraContent(navController)
                            }
                            composable(ScreenData.Settings.route) {
                                SettingContent(navController)
                            }

                        }

                    }
                }
            }
        }
    }

    private fun SettingContent(navController: NavController) {

    }



    fun UserContent(navController: NavController) {

    }


}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Portfolio1Theme {
        Greeting("Android")
    }
}