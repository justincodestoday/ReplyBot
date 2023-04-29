package com.mandalorian.replybot.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mandalorian.replybot.service.AuthService
import com.mandalorian.replybot.ui.presentation.authDirectory.register.viewModel.RegisterViewModel
import junit.framework.Assert
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
class RegisterViewModelTest {
    @Rule
    @JvmField
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var registerViewModel: RegisterViewModel
    private val authRepo = Mockito.mock(AuthService::class.java)

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        registerViewModel = RegisterViewModel(authRepo)
    }

//    @Test
//    fun test() = runTest {
//        Mockito.`when`(authRepo.login("abc@abc.com", "qweqweqwe")).thenReturn(true)
//        registerViewModel.email.value = "abc@abc.com"
//        registerViewModel.password.value = "qweqweqwe"
//        registerViewModel.login()
//        Assert.assertEquals(registerViewModel.loginFinish.first(), Unit)
//    }
//
//    @Test
//    fun `user should not be able with the wrong credential`() = runTest {
//        Mockito.`when`(authRepo.login("abc@abc.com", "qweqweqwe")).thenReturn(false)
//        registerViewModel.email.value = "abc@abc.com"
//        registerViewModel.password.value = "qweqweqw"
//        registerViewModel.login()
//        Assert.assertEquals(registerViewModel.error.first(), "Login failed")
//    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }
}