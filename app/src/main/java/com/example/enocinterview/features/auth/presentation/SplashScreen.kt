package com.example.enocinterview.features.auth.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.enocinterview.core.utils.Resource
import com.example.enocinterview.features.auth.data.local.SessionManager
import com.example.enocinterview.features.auth.domain.usecases.LoginUseCase
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    navController: NavHostController,
    sessionManager: SessionManager,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(Unit) {
        delay(500)
        val token = sessionManager.getToken()
        val firstTime: Boolean = sessionManager.isFirstTime()
        if (firstTime) {
            viewModel.login("anonymousUser", "password")
        } else {
            if (token.isNullOrEmpty()) {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            } else {
                navController.navigate("home") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
    }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        when (loginState) {
            is Resource.Idle -> {
            }

            is Resource.Loading -> {
            }
            is Resource.Success -> {
                viewModel.sessionManager.setFirstTime(false)
                navController.navigate("home") {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
            is Resource.Error -> {
            }
        }
        // Title at the center of the screen
        Text(
            text = "Welcome Enoc Interview",
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.Center)
                .testTag("welcomeTag")
        )

        // Loading indicator at the bottom of the screen
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp) // Padding to add some space from the bottom
        )
    }


}