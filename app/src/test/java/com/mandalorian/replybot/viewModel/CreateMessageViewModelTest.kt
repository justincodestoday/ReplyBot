package com.mandalorian.replybot.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mandalorian.replybot.model.Message
import com.mandalorian.replybot.repository.MessageRepository
import com.mandalorian.replybot.ui.presentation.messageForm.viewModel.CreateMessageViewModel
import com.mandalorian.replybot.utils.Utils
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
    @Rule
    @JvmField
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var createMessageViewModel: CreateMessageViewModel
    private val messageRepo = Mockito.mock(MessageRepository::class.java)

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        createMessageViewModel = CreateMessageViewModel(messageRepo)
    }

    @Test
    fun test() = runTest {
        Mockito.`when`(
            messageRepo.addMessage(
                Message(
                    title = "Title",
                    receipt = "Receipt",
                    replyMsg = "Reply"
                )
            )
        ).thenReturn(Unit)
        createMessageViewModel.title.value = "Title"
        createMessageViewModel.receipt.value = "Receipt"
        createMessageViewModel.replyMsg.value = "Reply"
        val message = Message(
            title = createMessageViewModel.title.value,
            receipt = createMessageViewModel.receipt.value,
            replyMsg = createMessageViewModel.replyMsg.value
        )
        val validationStatus = message.let {
            Utils.validate(it.title, it.receipt, it.replyMsg)
        }
        if (validationStatus) {
            createMessageViewModel.addMessage(message)
        }
        assertEquals(createMessageViewModel.finish.first(), Unit)
    }

    @Test
    fun `user should not be able to create new message with blank information`() = runTest {
        Mockito.`when`(
            messageRepo.addMessage(
                Message(
                    title = "Title",
                    receipt = "Receipt",
                    replyMsg = "Reply"
                )
            )
        ).thenReturn(Unit)
        createMessageViewModel.title.value = ""
        createMessageViewModel.receipt.value = ""
        createMessageViewModel.replyMsg.value = ""
        val message = Message(
            title = createMessageViewModel.title.value,
            receipt = createMessageViewModel.receipt.value,
            replyMsg = createMessageViewModel.replyMsg.value
        )
        val validationStatus = message.let {
            Utils.validate(it.title, it.receipt, it.replyMsg)
        }
        if (!validationStatus) {
            createMessageViewModel.addMessage(message)
        }
        assertEquals(createMessageViewModel.error.first(), "Please provide the necessary information")
    }

//    @Test
//    fun `message should not be able to add if it's empty`() = runTest {
//        val nonEmptyMessage = Message(null,"title", "receipt", "replyMsg")
//
//        // Stub the addMessage method to return a specific result
//        Mockito.`when`(messageRepo.addMessage(nonEmptyMessage)).thenReturn(Unit)
//
//        // Call the addMessage method with an empty message
//        val emptyMessage = Message(null,"", "", "")
//        createMessageViewModel.addMessage(emptyMessage)
//
//        // Verify that the addMessage method was not called with the empty message
//        Mockito.verify(messageRepo, Mockito.never()).addMessage(emptyMessage)
//
//        // Verify that the empty message is still the same after the addMessage method is called
//        assertEquals(emptyMessage, Message(null,"", "", ""))
//    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }
}