package com.mandalorian.replybot.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mandalorian.replybot.service.AuthService
import com.mandalorian.replybot.ui.presentation.authDirectory.login.viewModel.LoginViewModel
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
class LoginViewModelTest {

    @Rule
    @JvmField
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: LoginViewModel
    private val authRepo = Mockito.mock(AuthService::class.java)

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = LoginViewModel(authRepo)
    }

    @Test
    fun test() = runTest {
        Mockito.`when`(authRepo.login("abc@abc.com", "qweqweqwe")).thenReturn(true)
        viewModel.email.value = "abc@abc.com"
        viewModel.password.value = "qweqweqwe"
        viewModel.login()
        assertEquals(viewModel.loginFinish.first(), Unit)
    }

    @Test
    fun `user should not be able with the wrong credential`() = runTest {
        Mockito.`when`(authRepo.login("abc@abc.com", "qweqweqwe")).thenReturn(false)
        viewModel.email.value = "abc@abc.com"
        viewModel.password.value = "qweqweqw"
        viewModel.login()
        assertEquals(viewModel.error.first(), "Login failed")
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }
}