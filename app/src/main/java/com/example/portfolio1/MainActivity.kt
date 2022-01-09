package com.example.portfolio1
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.portfolio1.database.entities.User
import com.example.portfolio1.ui.theme.Portfolio1Theme
import com.example.portfolio1.view.*
import com.example.portfolio1.viewModel.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val detailViewModel: DetailViewModel by viewModels()
    private val cameraViewModel: CameraViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()
    private val createUserViewModel: CreateUserViewModel by viewModels()

    sealed class ScreenData(
        val route: String,
        @StringRes val resourceId: Int,
        val icon: ImageVector
    ) {
        object Main : ScreenData("MainUser", R.string.main, Icons.Filled.AccountCircle)
        object Camera : ScreenData("Camera", R.string.camera, Icons.Filled.Search)
        object Settings : ScreenData("Settings", R.string.settings, Icons.Filled.Settings)
        object Detail : ScreenData("Detail", R.string.settings, Icons.Filled.Settings)
        object CreateUser : ScreenData("CreateUser", R.string.settings, Icons.Filled.Settings)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            val defaultUser = User(
                userTelephone = "default",
                userBirthday = "default",
                userLastname = "default",
                userFirstname = "default",
                title = "default",
                sha256 = "default",
                pictureMedium = "default",
                pictureSmall = "default" )

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
                                MainContent(navController, mainViewModel, detailViewModel)
                            }
                            composable(ScreenData.Detail.route) {
                                DetailContent(navController, detailViewModel, defaultUser, applicationContext)
                            }
                            composable(ScreenData.CreateUser.route) {
                                CreateUserContent(navController, createUserViewModel)
                            }
                            composable(ScreenData.Camera.route) {
                                CameraContent(navController, applicationContext, cameraViewModel, detailViewModel)
                            }
                            composable(ScreenData.Settings.route) {
                                SettingsContent(navController, settingsViewModel, applicationContext)
                            }

                        }
                    }
                }
            }
        }
    }

}


