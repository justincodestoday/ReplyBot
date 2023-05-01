package com.mandalorian.replybot.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mandalorian.replybot.model.Message
import com.mandalorian.replybot.repository.MessageRepository
import com.mandalorian.replybot.ui.presentation.home.deactivatedMessages.viewModel.DeactivatedMessagesViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class DeactivatedMessagesViewModelTest {
    @Rule
    @JvmField
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DeactivatedMessagesViewModel
    private val messageRepo: MessageRepository = Mockito.mock(MessageRepository::class.java)

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = DeactivatedMessagesViewModel(messageRepo)
    }

    @Test
    fun test() = runTest {
        val actualList: List<Message> = listOf(
            Message(null, "Message 1", "Receipt 1", "Reply 1", false),
            Message(null, "Message 2", "Receipt 2", "Reply 2", false)
        )
        Mockito.`when`(messageRepo.getAllMessages()).thenReturn(actualList)
        viewModel.getMessages()
        delay(1000)
        assertEquals(viewModel.messages.value?.size, actualList.size)
    }

    @Test
    fun `only fetches the deactivated messages`() = runTest {
        val actualList: List<Message> = listOf(
            Message(null, "Message 1", "Receipt 1", "Reply 1", false),
            Message(null, "Message 2", "Receipt 2", "Reply 2", true)
        )
        Mockito.`when`(messageRepo.getAllMessages()).thenReturn(actualList)
        viewModel.getMessages()
        delay(1000)
        assertNotEquals(viewModel.messages.value?.size, actualList.size)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }
}