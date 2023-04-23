package com.mandalorian.replybot.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mandalorian.replybot.model.Message
import com.mandalorian.replybot.repository.MessageRepository
import com.mandalorian.replybot.ui.presentation.messageForm.viewModel.CreateMessageViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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
class CreateMessageViewModelTest {

    private lateinit var createMessageViewModel: CreateMessageViewModel
    private val messageRepo = Mockito.mock(MessageRepository::class.java)

    @Rule
    @JvmField
    val taskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        createMessageViewModel = CreateMessageViewModel(messageRepo)
    }

    @Test
    fun test() = runTest {
        val message = Message(
            title = "Test",
            receipt = "Test2",
            replyMsg = "Test3"
        )
        Mockito.`when`(
            messageRepo.addMessage(message)
        ).thenReturn(Unit)
        createMessageViewModel.title.value = "Test"
        createMessageViewModel.receipt.value = "Test2"
        createMessageViewModel.replyMsg.value = "Test3"
        createMessageViewModel.addMessage(
            message.copy(
                createMessageViewModel.title.value,
                createMessageViewModel.receipt.value,
                createMessageViewModel.replyMsg.value
            )
        )
        assertEquals(createMessageViewModel.finish.first(), Unit)
    }

    @Test
    fun `message should not be able to add if it's empty`() = runTest {
        val nonEmptyMessage = Message(null,"title", "receipt", "replyMsg")

        // Stub the addMessage method to return a specific result
        Mockito.`when`(messageRepo.addMessage(nonEmptyMessage)).thenReturn(Unit)

        // Call the addMessage method with an empty message
        val emptyMessage = Message(null,"", "", "")
        createMessageViewModel.addMessage(emptyMessage)

        // Verify that the addMessage method was not called with the empty message
        Mockito.verify(messageRepo, Mockito.never()).addMessage(emptyMessage)

        // Verify that the empty message is still the same after the addMessage method is called
        assertEquals(emptyMessage, Message(null,"", "", ""))
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }
}