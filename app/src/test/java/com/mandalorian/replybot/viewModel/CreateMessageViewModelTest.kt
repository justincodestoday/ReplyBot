package com.mandalorian.replybot.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mandalorian.replybot.model.Message
import com.mandalorian.replybot.repository.MessageRepository
import com.mandalorian.replybot.ui.presentation.messageForm.createMessage.viewModel.CreateMessageViewModel
import com.mandalorian.replybot.utils.Utils
import junit.framework.TestCase.assertEquals
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

    private lateinit var viewModel: CreateMessageViewModel
    private val messageRepo = Mockito.mock(MessageRepository::class.java)

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = CreateMessageViewModel(messageRepo)
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
        viewModel.title.value = "Title"
        viewModel.receipt.value = "Receipt"
        viewModel.replyMsg.value = "Reply"
        val message = Message(
            title = viewModel.title.value,
            receipt = viewModel.receipt.value,
            replyMsg = viewModel.replyMsg.value
        )
        val validationStatus = message.let {
            Utils.validate(it.title, it.receipt, it.replyMsg)
        }
        if (validationStatus) {
            viewModel.addMessage(message)
        }
        assertEquals(viewModel.finish.first(), Unit)
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
            viewModel.addMessage(message)
        }
        assertEquals(viewModel.error.first(), "Please provide the necessary information")
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }
}