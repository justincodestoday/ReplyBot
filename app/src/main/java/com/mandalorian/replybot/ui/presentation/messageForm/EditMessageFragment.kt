package com.mandalorian.replybot.ui.presentation.messageForm

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.mandalorian.replybot.R
import com.mandalorian.replybot.ui.presentation.messageForm.viewModel.UpdateMessageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditMessageFragment : BaseMessageFragment() {
    override val viewModel: UpdateMessageViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.fragment_create_message

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)

        val args: EditMessageFragmentArgs by navArgs()
        viewModel.getMessageById(args.id)
        viewModel.message.observe(viewLifecycleOwner) {
            var status = it.isActivated
            binding?.apply {
                etTitle.setText(it.title)
                etSendMessage.setText(it.receipt)
                etReplyMessage.setText(it.replyMsg)
                switchToggle.isChecked = status
                tvName.setText("Edit Message")
                button.setText("Edit")
//                status = switchToggle.isChecked

                button.setOnClickListener {
                    val message = getMessage()
                    if(switchToggle.isChecked) {
                        message?.let {
                            viewModel.updateMessage(args.id, message, isActivated = true)
                        }
                    } else {
                        message?.let {
                            viewModel.updateMessage(args.id, message, isActivated = false)
                        }
                    }
                }
//                btnDelete.setOnClickListener {
//                    viewModel.deleteMessage(args.id)
//                }

                if(it.isActivated){
                    tvToggle.text = "Activated"
                    tvToggle.setTextColor(R.color.teal_200)
                }else{
                    tvToggle.text = "Deactivated"
                    tvToggle.setTextColor(R.color.red_500)
                }
            }
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)

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