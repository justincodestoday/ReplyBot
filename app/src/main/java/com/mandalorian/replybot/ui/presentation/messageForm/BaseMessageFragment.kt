package com.mandalorian.replybot.ui.presentation.messageForm

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mandalorian.replybot.R
import com.mandalorian.replybot.databinding.FragmentCreateMessageBinding
import com.mandalorian.replybot.databinding.MessageFormBinding
import com.mandalorian.replybot.model.Message
import com.mandalorian.replybot.ui.presentation.base.BaseFragment
import com.mandalorian.replybot.utils.Utils
import kotlinx.coroutines.launch


abstract class BaseMessageFragment : BaseFragment<FragmentCreateMessageBinding>() {
    override fun getLayoutResource(): Int = R.layout.fragment_create_message

    fun getMessage(): Message? {
        return binding?.run {
            val title = etTitle.text.toString()
            val sendMsg = etSendMessage.text.toString()
            val replyMsg = etReplyMessage.text.toString()

            val validationStatus = Utils.validate(
                etTitle.text.toString(),
                etSendMessage.text.toString(),
                etReplyMessage.text.toString(),
            )

            if(!validationStatus) {
                lifecycleScope.launch {
                    viewModel?.error?.emit("Im Done")
                }
                return null
            }

            Message(
                null, title, sendMsg, replyMsg
            )
        }
    }
}