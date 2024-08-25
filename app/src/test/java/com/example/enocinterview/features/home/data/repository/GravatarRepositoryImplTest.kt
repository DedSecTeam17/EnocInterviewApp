package com.example.enocinterview.features.home.data.repository


import com.example.enocinterview.core.utils.Resource
import com.example.enocinterview.features.home.data.api.GravatarApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior

@OptIn(ExperimentalCoroutinesApi::class)
class GravatarRepositoryImplTest {

    private lateinit var repository: GravatarRepositoryImpl
    private lateinit var mockGravatarApiService: MockGravatarApiService
    private lateinit var behavior: NetworkBehavior
    private lateinit var delegate: BehaviorDelegate<GravatarApiService>

    @Before
    fun setUp() {
        behavior = NetworkBehavior.create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.gravatar.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val mockRetrofit = MockRetrofit.Builder(retrofit)
            .networkBehavior(behavior)
            .build()

        delegate = mockRetrofit.create(GravatarApiService::class.java)
        mockGravatarApiService = MockGravatarApiService(delegate)

        repository = GravatarRepositoryImpl(mockGravatarApiService)
    }

    @Test
    fun `test getGravatarProfile success`() = runTest {
        // Simulate a successful response
        val result = repository.getGravatarProfile("validIdentifier").toList()

        println(result)


        // Verify the result
        assert(result[1] is Resource.Success)
        val profile = (result[1] as Resource.Success).data
        assertEquals("Alex Morgan", profile.display_name)
        assertEquals("https://0.gravatar.com/avatar/33252cd1f33526af53580fcb1736172f06e6716f32afdd1be19ec3096d15dea5", profile.avatar_url)
    }

    @Test
    fun `test getGravatarProfile error`() = runTest {
        // Simulate an error response
        val result = repository.getGravatarProfile("error").toList()

        println(result)
        // Verify the result
        assert(result[1] is Resource.Success)
        val errorMessage = (result[1] as Resource.Success).data.error
        assertEquals("Not Found", errorMessage)
    }
}