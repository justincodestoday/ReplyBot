package com.mandalorian.replybot

import com.mandalorian.replybot.utils.Utils.validate
import com.mandalorian.replybot.utils.ValidationUtils.validateEmail
import com.mandalorian.replybot.utils.ValidationUtils.validateUsername
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ValidationUtilTest {
    @Test
    fun `should return false if any field is empty`() {
        assertEquals(validate(""), false)
    }

    @Test
    fun `should return true if none of the fields is empty`() {
        assertEquals(validate("Peter"), true)
    }

    @Test
    fun `empty email should not pass the validation`() {
        assertEquals(validateEmail(""), false)
    }

    @Test
    fun `should return false if @ is not included`() {
        assertEquals(validateEmail("abc.com"), false)
    }

    @Test
    fun `should return false if email starts with special character`() {
        assertEquals(validateEmail(".abc@abc@gmail.com"), false)
    }

    @Test
    fun `email should not contain any special character other than @ and dot`() {
        assertEquals(validateEmail("abc#$%aa@abc.com"), false)
    }

    @Test
    fun `valid email address should pass the test`() {
        assertEquals(validateEmail("a@a.com"), true)
    }

    @Test
    fun `if user name contains special characters, it should fail the test`() {
        assertEquals(validateUsername("p3t3rp@rk3r123"), false)
    }

    @Test
    fun `if user name contains only alphanumeric characters, it should pass the test`() {
        assertEquals(validateUsername("peterparker123"), true)
    }

    @Test
    fun `if password is less than 8 characters, it will fail the test`() {
        assertEquals(validateUsername("abcde"), false)
    }

    @Test
    fun `if password contains special characters, it should pass the test`() {
        assertEquals(validateUsername("abcde123!"), false)
    }

    @Test
    fun `valid password should pass the test`() {
        assertEquals(validateUsername("abcde123"), true)
    }
}