package com.mandalorian.replybot.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mandalorian.replybot.model.Message
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
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class ActivatedMessagesViewModelTest {
    @Rule
    @JvmField
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ActivatedMessagesViewModel
    private val messageRepo: MessageRepository = Mockito.mock(MessageRepository::class.java)
    private val expectedList: MutableList<Message> = mutableListOf()

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = ActivatedMessagesViewModel(messageRepo)
    }

    @Test
    fun `when getMessages called it should get messages`() = runTest {
        viewModel.getMessages()
        verify(messageRepo).getAllMessages()
    }

//    @Test
//    fun test() = runTest {
//        val actualList: List<Message> = listOf(
//            Message(null, "Message 1", "Receipt 1", "Reply 1", true),
//            Message(null, "Message 2", "Receipt 2", "Reply 2", false)
//        )
////        val expectedList: MutableList<Message> = mutableListOf()
//        Mockito.`when`(messageRepo.getAllMessages()).thenReturn(actualList)
//        viewModel.getMessages()
//        verify(messageRepo).getAllMessages()
//
////        delay(2000)
//        assertEquals(viewModel.messages.value?.size, actualList.size)
//    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }
}