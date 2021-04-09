package com.example.composenavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.composenavigation.ui.theme.ComposeNavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNavigationTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    BottomNavigationScreen()
                }
            }
        }
    }
}

// -- bottom navigation --

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen("home", R.string.home)
    object FriendsList : Screen("friendslist", R.string.friends_list)
}

@Composable
fun BottomNavigationScreen() {
    val items = listOf(
        Screen.Home,
        Screen.FriendsList
    )
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = "some icon") },
                        label = { Text(stringResource(screen.resourceId)) },
                        selected = (currentRoute == screen.route),
                        onClick = {
                            navController.navigate(screen.route) {
                                // prevent adding each bottom nav location more than once to the
                                // back stack
                                popUpTo = navController.graph.startDestination
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    ) {
        NavHost(navController = navController, startDestination = Screen.Home.route) {
            composable(Screen.Home.route) {
                HomeScreen()
            }
            composable(Screen.FriendsList.route) {
                FriendsScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Text(
        text = "This is the home screen!"
    )
}

@Composable
fun FriendsScreen() {
    Column {
        repeat(3) {
            Text(
                text = "This is friend $it"
            )
        }
    }
}

// -- Basic navigation --

@Composable
fun ComposeNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "FirstScreen") {
        composable("FirstScreen") {
            FirstScreen(navController = navController)
        }
        composable("SecondScreen") {
            SecondScreen(navController = navController)
        }
        composable("ThirdScreen") {
            ThirdScreen(navController = navController)
        }
    }
}

@Composable
fun FirstScreen(navController: NavController) {
    Text(
        text = "This is the first screen",
        modifier = Modifier.clickable {
            navController.navigate("SecondScreen") {
                launchSingleTop = true
            }
        }
    )
}

@Composable
fun SecondScreen(navController: NavController) {
    Text(
        text = "This is the second screen",
        modifier = Modifier.clickable {
            navController.navigate("ThirdScreen") {
                launchSingleTop = true
            }
        }
    )
}

@Composable
fun ThirdScreen(navController: NavController) {
    Text(
        text = "This is the third screen, I take you back to the first screen!",
        modifier = Modifier.clickable {
            navController.navigate("FirstScreen") {
                launchSingleTop = true
            }
        }
    )
}

@Preview
@Composable
fun PreviewMainScreen() {
    ComposeNavigation()
}