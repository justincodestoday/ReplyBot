package com.mandalorian.replybot.ui.presentation.messageForm

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.mandalorian.replybot.ui.presentation.messageForm.viewModel.UpdateMessageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditMessageFragment : BaseMessageFragment() {
    override val viewModel: UpdateMessageViewModel by viewModels()

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)
    }


    override fun onBindData(view: View) {
        super.onBindData(view)

        val args: EditMessageFragmentArgs by navArgs()
        viewModel.getMessageById(args.id)
        viewModel.message.observe(viewLifecycleOwner) {
            binding?.apply {
                etTitle.setText(it.title)
                etSendMessage.setText(it.sendMsg)
                etReplyMessage.setText(it.replyMsg)

                button.setOnClickListener {
                    val message = getMessage()
                    message?.let {
                        viewModel.updateMessage(args.id, message)
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.finish.collect {
                val bundle = Bundle()
                bundle.putBoolean("refresh", true)
                setFragmentResult("from_update", bundle)
                navController.popBackStack()
            }
        }
    }
}