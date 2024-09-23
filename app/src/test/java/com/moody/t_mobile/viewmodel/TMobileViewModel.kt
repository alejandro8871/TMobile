package com.moody.t_mobile.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.moody.t_mobile.model.CardsList
import com.moody.t_mobile.model.Page
import com.moody.t_mobile.repository.TMobileRepository
import com.moody.t_mobile.sealed.TMobileUiState
import com.moody.t_mobile.util.isInternetAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@Suppress("DEPRECATION")
@ExperimentalCoroutinesApi
class TMobileViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TMobileViewModel
    private val repository: TMobileRepository = mock()
    private val application: Application = mock()

    private val connectivityManager: ConnectivityManager = mock()
    private val networkInfo: NetworkInfo = mock {
        on { isConnected }.thenReturn(true) // Simulate a connected state
    }

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher()) // Set a test dispatcher
        viewModel = TMobileViewModel(repository, application)

        // Mock the application to return the mocked ConnectivityManager
        whenever(application.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(
            connectivityManager
        )
        whenever(connectivityManager.activeNetworkInfo).thenReturn(networkInfo) // Return the mocked NetworkInfo
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher after tests
    }

    @Test
    fun `fetchFeedList should update itemsData with Success when data is fetched`() = runTest {
        // Given
        val page = Page(CardsList(cards = listOf()))
        whenever(repository.getItemsFeed()).thenReturn(page)

        // Observe LiveData
        val observer: Observer<TMobileUiState> = mock()
        viewModel.itemsData.observeForever(observer)

        // When
        viewModel.fetchFeedList() // You may need to call it explicitly

        // Then
        val captor = ArgumentCaptor.forClass(TMobileUiState::class.java)
        verify(
            observer,
            times(2)
        ).onChanged(captor.capture()) // Called initially and once after fetch
        assert(captor.allValues[1] is TMobileUiState.Success) // Check for Success state
    }

    @Test
    fun `fetchFeedList should update itemsData with Error when an exception occurs`() = runTest {
        // Given
        val exception = Exception("Network Error")
        whenever(repository.getItemsFeed()).thenThrow(exception)

        // Observe LiveData
        val observer: Observer<TMobileUiState> = mock()
        viewModel.itemsData.observeForever(observer)

        // When
        viewModel.fetchFeedList()

        // Then
        val captor = ArgumentCaptor.forClass(TMobileUiState::class.java)
        verify(observer, times(2)).onChanged(captor.capture())
        assert(captor.allValues[1] is TMobileUiState.Error) // Check for Error state
    }

    @Test
    fun `init should check internet connection and fetch data if connected`() = runTest {
        // Given
        whenever(isInternetAvailable(application)).thenReturn(true)
        val page = Page(CardsList(cards = listOf()))
        whenever(repository.getItemsFeed()).thenReturn(page)

        // Observe LiveData
        val observer: Observer<TMobileUiState> = mock()
        viewModel.itemsData.observeForever(observer)

        // Then
        assert(viewModel.itemsData.value is TMobileUiState.Loading) // Initial loading state
        advanceUntilIdle() // Advance until all coroutines are completed

        assert(viewModel.itemsData.value is TMobileUiState.Success) // Check for Success state
    }
}