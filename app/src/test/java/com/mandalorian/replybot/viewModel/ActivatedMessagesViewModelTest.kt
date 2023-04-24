package com.mandalorian.replybot.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mandalorian.replybot.model.Message
import com.mandalorian.replybot.model.User
import com.mandalorian.replybot.repository.MessageRepository
import com.mandalorian.replybot.ui.presentation.home.activatedMessages.viewModel.ActivatedMessagesViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class ActivatedMessagesViewModelTest {
    @Rule
    @JvmField
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var activatedViewModel: ActivatedMessagesViewModel
    private val messageRepo = Mockito.mock(MessageRepository::class.java)

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        activatedViewModel = ActivatedMessagesViewModel(messageRepo)
    }

    @Test
    fun test() = runTest {
        val list: List<Message> = listOf()
        Mockito.`when`(messageRepo.getAllMessages()).thenReturn(list)
        val retrievedList = activatedViewModel.getMessages()
        assertEquals(retrievedList, list)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }
}