package com.mandalorian.replybot.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mandalorian.replybot.model.Message
import com.mandalorian.replybot.repository.MessageRepository
import com.mandalorian.replybot.ui.presentation.messageForm.updateMessage.viewModel.UpdateMessageViewModel
import com.mandalorian.replybot.utils.Utils
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateMessageViewModelTest {
    @Rule
    @JvmField
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: UpdateMessageViewModel
    private val messageRepo = Mockito.mock(MessageRepository::class.java)

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = UpdateMessageViewModel(messageRepo)
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
        viewModel.title.value = "New Title"
        viewModel.receipt.value = "New Receipt"
        viewModel.replyMsg.value = "New Reply"
        val message = Message(
            title = viewModel.title.value,
            receipt = viewModel.receipt.value,
            replyMsg = viewModel.replyMsg.value
        )
        val validationStatus = message.let {
            Utils.validate(it.title, it.receipt, it.replyMsg)
        }
        if (validationStatus) {
            viewModel.updateMessage("1", message, false)
        }
        assertEquals(viewModel.finish.first(), Unit)
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
        viewModel.title.value = ""
        viewModel.receipt.value = ""
        viewModel.replyMsg.value = ""
        val message = Message(
            title = viewModel.title.value,
            receipt = viewModel.receipt.value,
            replyMsg = viewModel.replyMsg.value
        )
        val validationStatus = message.let {
            Utils.validate(it.title, it.receipt, it.replyMsg)
        }
        if (!validationStatus) {
            viewModel.updateMessage("1", message, false)
        }
        assertEquals(viewModel.error.first(), "Please provide the necessary information")
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }
}