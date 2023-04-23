package com.mandalorian.replybot

import com.mandalorian.replybot.utils.ValidationUtil
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ValidationUntilTest {

    @Test
    fun `should return false if email is empty`() {
        assertEquals(ValidationUtil.validateEmail(""),false)
    }

    @Test
    fun `should return false if @ is not included`() {
        assertEquals(ValidationUtil.validateEmail("abc.com"),false)
    }

    @Test
    fun `email should not contains any special characters other than @ and full stop`() {
        assertEquals(ValidationUtil.validateEmail("abc#$%aa@abc.com"),false)
    }


    @Test
    fun `should return false if email start with special character`() {
        assertEquals(ValidationUtil.validateEmail(".abc@abc.com"),false)
    }

    @Test
    fun `valid email address should pass the test`(){
        assertEquals(ValidationUtil.validateEmail("khayrul123"),false)
    }

    @Test
    fun `user name should contains only alphanumeric characters`(){
        assertEquals(ValidationUtil.validateUserName("%#khayrul123"),false)
    }
}