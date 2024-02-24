package com.example.homework23

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito


class MyViewModelTest{
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun getSuccessResponse() {
        val repository = Mockito.mock(Repository::class.java)
        val successfulResponse = BitcoinResponse(Data("crypto"))
        val viewModel = MyViewModel(repository)
        var eventList = mutableListOf<MyViewModel.UIState>()
        viewModel.uiState.observeForever {
            eventList.add(it)
        }
        runBlocking {
            Mockito.`when`(repository.getCurrencyByName("bitcoin")).thenReturn(successfulResponse)
        }
        viewModel.getData()
        Assert.assertEquals(MyViewModel.UIState.NoData, eventList[0])
        Assert.assertEquals(MyViewModel.UIState.Processing, eventList[1])
        val weatherState = eventList[2] as MyViewModel.UIState.UpdatedData
        Assert.assertEquals("crypto", weatherState.type)
    }

    @Test
    fun getNullResponse() {
        val repository = Mockito.mock(Repository::class.java)
        val nullResponse = BitcoinResponse(null)
        val viewModel = MyViewModel(repository)
        var eventList = mutableListOf<MyViewModel.UIState>()
        viewModel.uiState.observeForever {
            eventList.add(it)
        }
        runBlocking {
            Mockito.`when`(repository.getCurrencyByName("bitcoin")).thenReturn(nullResponse)
        }

        viewModel.getData()
        Assert.assertEquals(MyViewModel.UIState.NoData, eventList[0])
        Assert.assertEquals(MyViewModel.UIState.Processing, eventList[1])
        Assert.assertEquals(MyViewModel.UIState.Error, eventList[2])
    }
}