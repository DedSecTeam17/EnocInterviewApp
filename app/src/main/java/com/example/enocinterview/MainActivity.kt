package com.example.enocinterview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.enocinterview.features.home.presentation.HomeScreen
import com.example.enocinterview.features.auth.data.local.SessionManager
import com.example.enocinterview.features.auth.presentation.LoginScreen
import com.example.enocinterview.features.auth.presentation.LoginViewModel
import com.example.enocinterview.features.auth.presentation.SplashScreen
import com.example.enocinterview.ui.theme.EnocInterviewTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val sessionManager = hiltViewModel<LoginViewModel>().sessionManager

            EnocInterviewTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    sessionManager?.let { MyApp(navController = navController, sessionManager = it) }
                }
            }
        }
    }
}

@Composable
fun MyApp(navController: NavHostController, sessionManager: SessionManager) {

    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") { SplashScreen(navController, sessionManager = sessionManager) }
        composable("login") { LoginScreen(navController) }
        composable("home") { HomeScreen(navController)}
    }
}








