package com.mandalorian.replybot.utils

object ValidationUtils {
    fun validateEmail(email: String): Boolean {
        val emailReg = Regex(
            "[a-z\\d]{1,256}(\\.[a-z\\d]){0,25}" +
                    "@" +
                    "[a-z\\d]+[a-z\\d]{0,64}" +
                    "(\\.[a-z\\d][a-z\\d]{0,25})+",
            RegexOption.IGNORE_CASE
        )
        if (emailReg.matches(email)) {
            return true
        }
        return false
    }

    fun validateUsername(name: String): Boolean {
        if (Regex("[a-z\\d]{5,20}", RegexOption.IGNORE_CASE).matches(name)) {
            return true
        }
        return false
    }

    fun validatePassword(password: String): Boolean {
        if (Regex("[a-z\\d]{8,20}", RegexOption.IGNORE_CASE).matches(password)) {
            return true
        }
        return false
    }
}