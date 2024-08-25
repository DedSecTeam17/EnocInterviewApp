package com.example.enocinterview.features.home.presentation

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.viewModels
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.rule.GrantPermissionRule
import com.example.enocinterview.MainActivity
import com.example.enocinterview.features.auth.presentation.LoginViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class TestHomeFlow {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule(order = 2)
    val permissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )


    @Before
    fun setup() {
        hiltRule.inject()
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    //    MARK: TEST LOGIN [ WHERE USER CLEAR SESSION BEFORE
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testHomeScreenWhereImageSelectedFromGallery(): Unit = runBlocking {

        composeTestRule.activity.viewModels<LoginViewModel>().value.sessionManager.setFirstTime(
            false
        )
        composeTestRule.activity.viewModels<LoginViewModel>().value.sessionManager.saveSession(
            userId = "",
            token = "",
            password = "",
            email = ""
        )

        composeTestRule.onAllNodesWithTag("welcomeTag").onFirst().assertIsDisplayed()

        composeTestRule.waitUntilAtLeastOneExists(hasText("Login"), timeoutMillis = 500000)

        composeTestRule.onNodeWithTag("emailField").performTextInput("user@example.com")
        composeTestRule.onNodeWithTag("passwordField").performTextInput("validPassword")
        composeTestRule.onNodeWithTag("login_id").assertIsDisplayed()
        composeTestRule.onNodeWithTag("login_id").performClick()


        composeTestRule.waitUntilAtLeastOneExists(
            hasTestTag("user_name_label"),
            timeoutMillis = 100000
        )

        composeTestRule.waitUntilAtLeastOneExists(
            hasTestTag("select_image_tag_avatar"),
            timeoutMillis = 100000
        )




        composeTestRule.onNodeWithTag("user_name_label").assertIsDisplayed()
        composeTestRule.onNodeWithTag("select_image_tag_avatar").assertIsDisplayed()

        composeTestRule.onNodeWithTag("select_image_tag_avatar").performClick()

        composeTestRule.waitUntilAtLeastOneExists(
            hasTestTag("select_image_bottom_sheet"),
            timeoutMillis = 100000
        )

        composeTestRule.onNodeWithTag("select_from_gallery").assertIsDisplayed()
        composeTestRule.onNodeWithTag("select_from_camera").assertIsDisplayed()

        composeTestRule.onNodeWithTag("select_from_gallery").performClick()


        // Create a mock Uri for the image
        val mockImageUri = Uri.parse("content://media/external/images/media/1")

        // Stub the intent for selecting an image from the gallery
        val resultData = Intent().apply { data = mockImageUri }
        intending(
            Matchers.allOf(
                IntentMatchers.hasAction(Intent.ACTION_PICK),
                IntentMatchers.hasType("image/*")
            )
        ).respondWith(
            Instrumentation.ActivityResult(Activity.RESULT_OK, resultData)
        )

    }


    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testHomeScreenWhereImageSelectedFromCamera(): Unit = runBlocking {

        composeTestRule.activity.viewModels<LoginViewModel>().value.sessionManager.setFirstTime(
            false
        )
        composeTestRule.activity.viewModels<LoginViewModel>().value.sessionManager.saveSession(
            userId = "",
            token = "",
            password = "",
            email = ""
        )

        composeTestRule.onAllNodesWithTag("welcomeTag").onFirst().assertIsDisplayed()

        composeTestRule.waitUntilAtLeastOneExists(hasText("Login"), timeoutMillis = 500000)

        composeTestRule.onNodeWithTag("emailField").performTextInput("user@example.com")
        composeTestRule.onNodeWithTag("passwordField").performTextInput("validPassword")
        composeTestRule.onNodeWithTag("login_id").assertIsDisplayed()
        composeTestRule.onNodeWithTag("login_id").performClick()


        composeTestRule.waitUntilAtLeastOneExists(
            hasTestTag("user_name_label"),
            timeoutMillis = 100000
        )

        composeTestRule.waitUntilAtLeastOneExists(
            hasTestTag("select_image_tag_avatar"),
            timeoutMillis = 100000
        )




        composeTestRule.onNodeWithTag("user_name_label").assertIsDisplayed()
        composeTestRule.onNodeWithTag("select_image_tag_avatar").assertIsDisplayed()

        composeTestRule.onNodeWithTag("select_image_tag_avatar").performClick()

        composeTestRule.waitUntilAtLeastOneExists(
            hasTestTag("select_image_bottom_sheet"),
            timeoutMillis = 100000
        )

        composeTestRule.onNodeWithTag("select_from_gallery").assertIsDisplayed()

        composeTestRule.onNodeWithTag("select_from_camera").assertIsDisplayed()




        composeTestRule.onNodeWithTag("select_from_camera").performClick()

        // Create a mock Uri for the image
        val mockImageUri = Uri.parse("content://media/external/images/media/2")

        // Stub the intent for capturing an image from the camera
        val resultData = Intent().apply { data = mockImageUri }
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(
            Instrumentation.ActivityResult(Activity.RESULT_OK, resultData)
        )
//



    }
}
