package com.example.enocinterview.features.home.domain

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.enocinterview.core.utils.Resource
import com.example.enocinterview.features.auth.data.model.LoginResponse
import com.example.enocinterview.features.home.data.model.AvatarUpdateResponse
import com.example.enocinterview.features.home.data.model.GravatarProfile
import com.example.enocinterview.features.home.data.model.UserResponse
import com.example.enocinterview.features.home.domain.usecases.GetGravatarProfileUseCase
import com.example.enocinterview.features.home.domain.usecases.GetUserUseCase
import com.example.enocinterview.features.home.domain.usecases.UpdateAvatarUseCase
import com.example.enocinterview.features.home.presentation.UserViewModel
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
class UserViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule() // For LiveData

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var getUserUseCase: GetUserUseCase

    @Mock
    private lateinit var updateAvatarUseCase: UpdateAvatarUseCase

    @Mock
    private lateinit var getGravatarProfileUseCase: GetGravatarProfileUseCase

    private lateinit var viewModel: UserViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        Dispatchers.setMain(testDispatcher)
        viewModel = UserViewModel(
            getUserUseCase = getUserUseCase,
            updateAvatarUseCase = updateAvatarUseCase,
            getGravatarProfileUseCase = getGravatarProfileUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun `fetchUserData emits success`() = runTest {
        val userId = "mockUserId"
        val userResponse =
            UserResponse(email = "test@example.com", avatar_url = "https://gravatar.com/exampl")

        `when`(getUserUseCase(userId)).thenReturn(flow {
            emit(Resource.Loading)
            emit(Resource.Success(userResponse))
        })

        // Collect emitted states
        val stateList = mutableListOf<Resource<UserResponse>>()
        val job = launch {
            viewModel.userState.collect { state ->
                println(state)
                stateList.add(state)
            }
        }

        viewModel.fetchUserData(userId)

        runCurrent()

        // Cancel the job after collecting
        job.cancel()

//        val result = viewModel.userState.toList()
        println(stateList)
        assert(stateList[1] is Resource.Success)
        assertEquals(userResponse, (stateList[1] as Resource.Success).data)
    }

    @Test
    fun `fetchGravatarProfile emits success`() = runTest {
        val email = "test@example.com"
        val gravatarProfile = GravatarProfile(
            hash = "31c5543c1734d25c7206f5fd591525d0295bec6fe84ff82f946a34fe970a1e66",
            display_name = "Alex Morgan",
            profile_url = "https://gravatar.com/example",
            avatar_url = "https://0.gravatar.com/avatar/33252cd1f33526af53580fcb1736172f06e6716f32afdd1be19ec3096d15dea5",
            avatar_alt_text = "Alex Morgan's avatar image. Alex is smiling and standing in beside a large dog who is looking up at Alex.",
            location = "New York, USA",
            description = "I like playing hide and seek.",
            job_title = "Landscape Architect",
            company = "ACME Corp",
            verified_accounts = emptyList(),
            pronunciation = "Al-ex Mor-gan",
            pronouns = "She/They",
            timezone = "Europe/Bratislava",
            languages = emptyList(),
            first_name = "Alex",
            last_name = "Morgan",
            is_organization = false,
            links = emptyList(),
            interests = emptyList(),
            payments = null,
            contact_info = null,
            gallery = emptyList(),
            number_verified_accounts = 3,
            last_profile_edit = "2021-10-01T12:00:00Z",
            registration_date = "2021-10-01T12:00:00Z",
            error = null
        )

        `when`(getGravatarProfileUseCase(viewModel.getSha256Hash(email))).thenReturn(flow {
            emit(Resource.Loading)
            emit(Resource.Success(gravatarProfile))
        })

        // Collect emitted states
        val stateList = mutableListOf<Resource<GravatarProfile>>()
        val job = launch {
            viewModel.gravatarProfileState.collect { state ->
                println(state)
                stateList.add(state)
            }
        }

        viewModel.fetchGravatarProfile(email)


        runCurrent()

        // Cancel the job after collecting
        job.cancel()

        assert(stateList[1] is Resource.Success)
        assertEquals(gravatarProfile, (stateList[1] as Resource.Success).data)
    }

    @Test
    fun `updateUserAvatar emits success and fetches user data`() = runTest {
        val userId = "mockUserId"
        val base64String = "mockBase64String"
        val avatarUpdateResponse = AvatarUpdateResponse("https://new-avatar-url.com")
        val userResponse = UserResponse(email = "test@example.com", avatar_url = "url")

        `when`(updateAvatarUseCase(userId, base64String)).thenReturn(flow {
            emit(Resource.Loading)
            emit(Resource.Success(avatarUpdateResponse))
        })

        `when`(getUserUseCase(userId)).thenReturn(flow {
            emit(Resource.Success(userResponse))
        })

        val avatarStateStateList = mutableListOf<Resource<AvatarUpdateResponse>>()
        val avatarJob = launch {
            viewModel.avatarState.collect { state ->
                println(state)
                avatarStateStateList.add(state)
            }
        }

        val userStateList = mutableListOf<Resource<UserResponse>>()
        val userJob = launch {
            viewModel.userState.collect { state ->
                println(state)
                userStateList.add(state)
            }
        }

        viewModel.updateUserAvatar(
            context = mock(Context::class.java),
            userId = userId,
            base64String = base64String
        )

        runCurrent()

        // Cancel the job after collecting
        avatarJob.cancel()
        userJob.cancel()

        println(avatarStateStateList)
        println(userStateList)


        assert(userStateList[1] is Resource.Success)
        assertEquals(userResponse, (userStateList[1] as Resource.Success).data)
    }
}