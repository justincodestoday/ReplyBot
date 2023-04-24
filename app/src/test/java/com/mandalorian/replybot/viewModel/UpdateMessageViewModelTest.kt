package com.mandalorian.replybot.viewModel

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mandalorian.replybot.model.Message
import com.mandalorian.replybot.repository.MessageRepository
import com.mandalorian.replybot.ui.presentation.messageForm.viewModel.UpdateMessageViewModel
import com.mandalorian.replybot.utils.Utils
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateMessageViewModelTest {
    @Rule
    @JvmField
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var updateMessageViewModel: UpdateMessageViewModel
    private val messageRepo = Mockito.mock(MessageRepository::class.java)

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        updateMessageViewModel = UpdateMessageViewModel(messageRepo)
    }

    @Test
    fun test() = runTest {
        Mockito.`when`(
            messageRepo.updateMessage(
                "1",
                Message(
                    title = "New Title",
                    receipt = "New Receipt",
                    replyMsg = "New Reply"
                ),
                false
            )
        ).thenReturn(Unit)
        updateMessageViewModel.title.value = "New Title"
        updateMessageViewModel.receipt.value = "New Receipt"
        updateMessageViewModel.replyMsg.value = "New Reply"
        val message = Message(
            title = updateMessageViewModel.title.value,
            receipt = updateMessageViewModel.receipt.value,
            replyMsg = updateMessageViewModel.replyMsg.value
        )
        val validationStatus = message.let {
            Utils.validate(it.title, it.receipt, it.replyMsg)
        }
        if (validationStatus) {
            updateMessageViewModel.updateMessage("1", message, false)
        }
        assertEquals(updateMessageViewModel.finish.first(), Unit)
    }

    @Test
    fun `User should not be able to update message with blank information`() = runTest {
        Mockito.`when`(
            messageRepo.updateMessage(
                "1",
                Message(
                    title = "New Title",
                    receipt = "New Receipt",
                    replyMsg = "New Reply"
                ),
                false
            )
        ).thenReturn(Unit)
        updateMessageViewModel.title.value = ""
        updateMessageViewModel.receipt.value = ""
        updateMessageViewModel.replyMsg.value = ""
        val message = Message(
            title = updateMessageViewModel.title.value,
            receipt = updateMessageViewModel.receipt.value,
            replyMsg = updateMessageViewModel.replyMsg.value
        )
        val validationStatus = message.let {
            Utils.validate(it.title, it.receipt, it.replyMsg)
        }
        if (!validationStatus) {
            updateMessageViewModel.updateMessage("1", message, false)
        }
        assertEquals(updateMessageViewModel.error.first(), "Please provide the necessary information")
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }
}