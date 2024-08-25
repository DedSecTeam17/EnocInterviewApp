package com.example.enocinterview.features.auth.presentation


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
import androidx.activity.viewModels
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.rule.GrantPermissionRule
import com.example.enocinterview.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class TestLoginFlow {

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
    }


    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testNewToAppFlow(): Unit = runBlocking {

        composeTestRule.activity.viewModels<LoginViewModel>().value.sessionManager.setFirstTime(true)

        composeTestRule.onAllNodesWithTag("welcomeTag").onFirst().assertIsDisplayed()

        composeTestRule.waitUntilAtLeastOneExists(
            hasTestTag("user_name_label"),
            timeoutMillis = 100000
        )

        composeTestRule.onNodeWithTag("user_name_label").assertIsDisplayed()

    }



//    MARK: TEST LOGIN [ WHERE USER CLEAR SESSION BEFORE
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testLoginScreenWithValidValues(): Unit = runBlocking {

        composeTestRule.activity.viewModels<LoginViewModel>().value.sessionManager.setFirstTime(false)
        composeTestRule.activity.viewModels<LoginViewModel>().value.sessionManager.saveSession(userId = "", token = "", password = "", email = "")

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

        composeTestRule.onNodeWithTag("user_name_label").assertIsDisplayed()

    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testLoginScreenWithEmptyValues(): Unit = runBlocking {

        composeTestRule.activity.viewModels<LoginViewModel>().value.sessionManager.setFirstTime(false)
        composeTestRule.activity.viewModels<LoginViewModel>().value.sessionManager.saveSession(userId = "", token = "", password = "", email = "")

        composeTestRule.onAllNodesWithTag("welcomeTag").onFirst().assertIsDisplayed()

        composeTestRule.waitUntilAtLeastOneExists(hasText("Login"), timeoutMillis = 500000)

        composeTestRule.onNodeWithTag("emailField").performTextInput("")
        composeTestRule.onNodeWithTag("passwordField").performTextInput("")
        composeTestRule.onNodeWithTag("login_id").assertIsDisplayed()
        composeTestRule.onNodeWithTag("login_id").performClick()

        composeTestRule.waitUntilAtLeastOneExists(
            hasTestTag("input_error_key"),
            timeoutMillis = 100000
        )

        composeTestRule.onNodeWithText("Invalid username").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password must be at least 6 characters long").assertIsDisplayed()

    }


    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testLoginScreenWithInvalidValues(): Unit = runBlocking {

        composeTestRule.activity.viewModels<LoginViewModel>().value.sessionManager.setFirstTime(false)
        composeTestRule.activity.viewModels<LoginViewModel>().value.sessionManager.saveSession(userId = "", token = "", password = "", email = "")

        composeTestRule.onAllNodesWithTag("welcomeTag").onFirst().assertIsDisplayed()

        composeTestRule.waitUntilAtLeastOneExists(hasText("Login"), timeoutMillis = 500000)

        composeTestRule.onNodeWithTag("emailField").performTextInput("a")
        composeTestRule.onNodeWithTag("passwordField").performTextInput("a")
        composeTestRule.onNodeWithTag("login_id").assertIsDisplayed()
        composeTestRule.onNodeWithTag("login_id").performClick()

        composeTestRule.waitUntilAtLeastOneExists(
            hasTestTag("input_error_key"),
            timeoutMillis = 100000
        )

        composeTestRule.onNodeWithText("Invalid username").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password must be at least 6 characters long").assertIsDisplayed()

    }
}
