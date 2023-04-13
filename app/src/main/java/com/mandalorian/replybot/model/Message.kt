package com.mandalorian.replybot.model

data class Message(
    val id: String? = null,
    val title: String? = "",
    val sendMsg: String? = "",
    val replyMsg: String? = "",
    var isActivated: Boolean = true,
)