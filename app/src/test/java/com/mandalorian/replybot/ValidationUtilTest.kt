package com.mandalorian.replybot

import com.mandalorian.replybot.utils.ValidationUtils.validateEmail
import com.mandalorian.replybot.utils.ValidationUtils.validateUsername
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ValidationUtilTest {

    @Test
    fun `empty email should not pass the validation`() {
        assertEquals(validateEmail(""), false)
    }

    @Test
    fun `should return false if @ is not included`() {
        assertEquals(validateEmail("abc.com"), false)
    }

    @Test
    fun `email should not contain any special character other than @ and full stop`() {
        assertEquals(validateEmail("ab.com"), false)
    }

    @Test
    fun `should return false if email start with special character`() {
        assertEquals(validateEmail(".abc@abc@gmail.com"), false)
    }

    @Test
    fun `user name should contain only alphanumeric characters`() {
        assertEquals(validateEmail("khayrul123"), false)
    }

    @Test
    fun `valid email address should pass the test`() {
        assertEquals(validateEmail("a@a.com"), true)
    }

    @Test
    fun `if user name contains only alphanumeric characters, it should pass the test`() {
        assertEquals(validateUsername("xiangze123"), true)
    }
}