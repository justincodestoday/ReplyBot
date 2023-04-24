package com.mandalorian.replybot

import com.mandalorian.replybot.utils.Utils
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ValidationUntilTest {
    @Test
    fun `should return false if any field is empty`() {
        assertEquals(Utils.validate(""), false)
    }

    @Test
    fun `should return true if none of the fields is empty`() {
        assertEquals(Utils.validate("Justin"), true)
    }
}