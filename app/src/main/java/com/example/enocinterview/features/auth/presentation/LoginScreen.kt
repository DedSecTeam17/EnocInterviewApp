package com.example.enocinterview.features.auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.enocinterview.core.ui.CustomTextField
import com.example.enocinterview.core.ui.FullWidthButton
import com.example.enocinterview.core.utils.GetScreenWidth
import com.example.enocinterview.core.utils.Resource

@Composable
fun LoginScreen(navController: NavHostController,viewModel: LoginViewModel = hiltViewModel()) {
//    val loginState by viewModel.loginState.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.loginState.collect { loginState ->

            when (loginState) {
                is Resource.Idle -> {

                }
                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    // Navigate to home screen or handle success
                    navController.navigate("home") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
                is Resource.Error -> {
                }

                else -> {}
            }
        }
    }

    LoginContent { username, password ->
        viewModel.login(username, password)
    }


}

@Composable
fun LoginContent(onLoginClicked: (username: String , password: String) -> Unit) {
    var username by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }

    var isEmailUsername by remember { mutableStateOf(true) }
    var isPasswordValid by remember { mutableStateOf(true) }



    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CustomTextField(
                    label = "Enter user name",
                    value = username,
                    onValueChange = { username = it },
                    keyboardType = KeyboardType.Email,
                    isValid = { it.length >= 6 },
                    errorMessage = "Invalid username",
                    isError = !isEmailUsername,
                    testTag = "emailField"
                )

                // Password TextField
                CustomTextField(
                    label = "Enter password",
                    value = password,
                    onValueChange = { password = it },
                    keyboardType = KeyboardType.Password,
                    isValid = { it.length >= 6 },
                    errorMessage = "Password must be at least 6 characters long",
                    isError = !isPasswordValid,
                    testTag = "passwordField"
                )

                FullWidthButton(
                    text = "Login",
                    height = 50.0,
                    width = GetScreenWidth()/1.2,
                    onClick = {


                        // Perform validation on button click
                        isEmailUsername = username.text.length >= 6
                        isPasswordValid = password.text.length >= 6

                        if (isEmailUsername && isPasswordValid) {
                            onLoginClicked(username.text.trim(), password.text.trim())
                        }
                    },
                    modifier = Modifier.testTag("login_id")
                )

            }
        }
    }
}

@Composable
@Preview
fun LoginContentPreview() {
    LoginContent { username, password ->

    }
}