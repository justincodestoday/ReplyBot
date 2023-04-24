package com.mandalorian.replybot.viewModel

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mandalorian.replybot.model.Message
import com.mandalorian.replybot.repository.MessageRepository
import com.mandalorian.replybot.ui.presentation.messageForm.viewModel.UpdateMessageViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateMessageViewModelTest {

    private lateinit var updateMessageViewModel: UpdateMessageViewModel
    private val messageRepo = Mockito.mock(MessageRepository::class.java)

    @Rule
    @JvmField
    val taskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        updateMessageViewModel = UpdateMessageViewModel(messageRepo)
    }

    @Test
    fun test() = runTest {
        val message = Message(
            id = "123",
            title = "Test",
            receipt = "Test2",
            replyMsg = "Test3",
            isActivated = true
        )
        val updatedMessage = message.copy(title = "Testing")
        Mockito.`when`(
            messageRepo.updateMessage("123", updatedMessage, false)
        ).thenReturn(updatedMessage)
        updateMessageViewModel.id.value = "123"
        updateMessageViewModel.title.value = "Testing"
        updateMessageViewModel.receipt.value = "Test2"
        updateMessageViewModel.replyMsg.value = "Test3"
        updateMessageViewModel.updateMessage(message.id.toString(), updatedMessage, message.isActivated)
//        val newMessage = updateMessageViewModel.message
//        newMessage.observeForever {
//            assertEquals(updatedMessage, it)
//        }
        assertEquals("Testing", updateMessageViewModel.title.value)
        assertEquals(false, updateMessageViewModel.isActivated.value)
//        assertEquals(updatedMessage, updateMessageViewModel.getMessageById(id = "123"))
    }

    @Test
    fun `message should be updated and activated`() = runTest {
        val message = Message(
            id = "123",
            title = "Test",
            receipt = "Test2",
            replyMsg = "Test3",
            isActivated = false
        )
        val activatedMessage = message.copy(title = "Testing", replyMsg = "Test8", isActivated = true)
        Mockito.`when`(
            messageRepo.updateMessage("123", activatedMessage, true)
        ).thenReturn(activatedMessage)
        updateMessageViewModel.id.value = "123"
        updateMessageViewModel.title.value = "Testing2"
        updateMessageViewModel.receipt.value = "Test2"
        updateMessageViewModel.replyMsg.value = "Test8"
        updateMessageViewModel.updateMessage(message.id.toString(), activatedMessage, true)
        val newMessage = updateMessageViewModel.message
        newMessage.observeForever {
            assertEquals(activatedMessage, it)
            assertEquals("Test8", it.replyMsg)
            assertEquals(true, it.isActivated)
        }
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }
}