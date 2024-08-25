package com.example.enocinterview.features.home.presentation

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.enocinterview.R
import com.example.enocinterview.core.utils.Base64Helper
import com.example.enocinterview.core.utils.Resource
import com.example.enocinterview.core.utils.UploadedImageSizeHelper
import com.example.enocinterview.features.auth.presentation.LoginViewModel
import kotlinx.coroutines.launch
import java.security.MessageDigest
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    userViewModel: UserViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val painter = painterResource(id = R.drawable.ic_avatar_placeholder)
    val context = LocalContext.current


    var avatarUri by remember { mutableStateOf<Uri?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }

    var hasCameraPermission by remember { mutableStateOf(false) }
    var hasStoragePermission by remember { mutableStateOf(false) }

    val userState by userViewModel.userState.collectAsState()

    val avatarState by userViewModel.avatarState.collectAsState()

    val gravatarProfileState by userViewModel.gravatarProfileState.collectAsState()


    var selectedFilter by remember { mutableStateOf<ColorFilter?>(null) }

    var gavatarImageUrl: String? by remember { mutableStateOf(null) }


    // Create a SnackbarHostState
    val snackbarHostState = remember { SnackbarHostState() }
    // Create a CoroutineScope tied to the Scaffold
    val scope = rememberCoroutineScope()


    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            hasCameraPermission = isGranted
        }

    // Launcher to request storage permission
    val storagePermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            hasStoragePermission = permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: false
        }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                avatarUri = it
                selectedFilter =
                    ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })


                println(getGravatarUrl(email = "mohammedelamin23.me@gmail.com"))

                if (UploadedImageSizeHelper.isUriFileSizeLessThan1MB(context, it)) {
                    loginViewModel.sessionManager.getUserId()
                        ?.let { userId ->
                            userViewModel.updateUserAvatar(
                                context,
                                userId = userId,
                                Base64Helper.uriToBase64(context, it)
                            )
                        }
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Image Size greater than 1 MB use smaller image",
                            actionLabel = "Dismiss",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            bitmap?.let {
                avatarUri =
                    uriFromBitmap(it)
                selectedFilter =
                    ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })

                if (UploadedImageSizeHelper.isBitmapSizeLessThanOrEqual1MB(it)) {
                    loginViewModel.sessionManager.getUserId()
                        ?.let { userId ->
                            userViewModel.updateUserAvatar(
                                context,
                                userId = userId,
                                Base64Helper.bitmapToBase64(it)
                            )
                        }
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Image Size greater than 1 MB use smaller image",
                            actionLabel = "Dismiss",
                            duration = SnackbarDuration.Short
                        )
                    }
                }


            }
        }


    // Request permissions as soon as the screen is composed
    LaunchedEffect(Unit) {
        // Check for camera permission
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)

        // Check for storage permission
        storagePermissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))

        loginViewModel.sessionManager.getUserId()?.let { userViewModel.fetchUserData(userId = it) }
        userViewModel.fetchGravatarProfile(email = loginViewModel.sessionManager.getEmail() ?: "")
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home") },
                actions = {
                    IconButton(onClick = {
                        // Handle logout action here
                        loginViewModel.sessionManager.clearSession()
                        navHostController.navigate("splash") {
                            popUpTo(navHostController.graph.startDestinationId) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_exit_to_app_24), // Replace with your logout icon
                            contentDescription = "Logout",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Gray)


            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {

                when (gravatarProfileState) {
                    is Resource.Error -> {

                    }

                    Resource.Idle -> {}
                    Resource.Loading -> {}
                    is Resource.Success -> {
                        val gravatarProfile = (gravatarProfileState as Resource.Success).data
                        gavatarImageUrl = gravatarProfile.avatar_url
                        println(gavatarImageUrl)
                    }
                }



                when (userState) {
                    is Resource.Error -> {}
                    Resource.Idle -> {}
                    Resource.Loading -> {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(bottom = 64.dp) // Padding to add some space from the bottom
                            )
                        }
                    }

                    is Resource.Success -> {
                        val user = (userState as Resource.Success).data
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Avatar Image
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Box(
//                                    modifier = Modifier
//                                        .size(80.dp)
//                                        .clip(CircleShape)
                                ) {
                                    avatarUri?.let {
                                        Image(
                                            painter = rememberAsyncImagePainter(model = it),
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.clickable {
//                                    show bottom sheet to select the image ===>
                                                showBottomSheet = true
                                            }.size(100.dp) // Adjust the size as needed
                                                .clip(CircleShape),
                                            colorFilter = selectedFilter
                                        )
                                    } ?: gavatarImageUrl?.let {
                                        Image(
                                            painter = rememberAsyncImagePainter(model = it),
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop, // Crop to fill the shape
                                            modifier = Modifier.clickable {
//                                    show bottom sheet to select the image ===>
                                                showBottomSheet = true
                                            }.size(100.dp) // Adjust the size as needed
                                                .clip(CircleShape) // Clip the image to a circle
                                        )
                                    } ?: Image(
                                        painter = painter,
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop, // Crop to fill the shape
                                        modifier = Modifier.clickable {
//                                    show bottom sheet to select the image ===>
                                            showBottomSheet = true
                                        }.size(100.dp) // Adjust the size as needed
                                            .clip(CircleShape),
                                        colorFilter = selectedFilter
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Email",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.testTag("user_name_label")
                            )
                            Text(
                                text = user.email,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            // Password (read-only label)
                            Text(
                                text = "Password",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = loginViewModel.sessionManager.getPassword() ?: "***",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                    }
                }

            }
        }
    }
    // Bottom Sheet
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = rememberModalBottomSheetState()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Select Image",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(onClick = {
                    showBottomSheet = false
                    if (hasStoragePermission) {
                        galleryLauncher.launch("image/*")
                    } else {
                        storagePermissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
                    }
                }) {
                    Text("Choose from Gallery")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    showBottomSheet = false
                    if (hasCameraPermission) {
                        cameraLauncher.launch(null)
                    } else {
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }) {
                    Text("Take Photo")
                }
            }
        }
    }

}


// Helper function to convert Bitmap to Uri (you would need to implement this)
fun uriFromBitmap(bitmap: Bitmap): Uri {
    // Implementation to convert Bitmap to Uri
    // This could involve saving the bitmap to a file and getting its Uri
    return Uri.EMPTY // Replace with actual implementation
}

fun getSha256Hash(email: String): String {
    val digest = MessageDigest.getInstance("SHA-256")
    val hash = digest.digest(email.trim().toLowerCase().toByteArray())
    return hash.joinToString("") { "%02x".format(it) }
}

fun getGravatarUrl(email: String, size: Int = 200): String {
    val hash = getSha256Hash(email)
    return "https://www.gravatar.com/avatar/$hash?s=$size"
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(navHostController = NavHostController(context = LocalContext.current))
}