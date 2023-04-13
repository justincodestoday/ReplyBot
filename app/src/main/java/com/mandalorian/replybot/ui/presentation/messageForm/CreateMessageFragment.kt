package com.mandalorian.replybot.ui.presentation.messageForm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mandalorian.replybot.R
import com.mandalorian.replybot.databinding.MessageFormBinding
import com.mandalorian.replybot.ui.presentation.base.BaseFragment
import com.mandalorian.replybot.ui.presentation.messageForm.viewModel.CreateMessageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateMessageFragment : BaseMessageFragment() {
    override val viewModel: CreateMessageViewModel by viewModels()
//    override fun getLayoutResource(): Int = R.layout.message_form

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)

        binding?.run {
            button.setOnClickListener {
                val message = getMessage()
                message.let {
                    if (message != null) {
                        Log.d("debugging", "Please la")
                        viewModel.addMessage(message)
                    }
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
                setFragmentResult("from_add_product", bundle)
                navController.popBackStack()
            }
        }
    }
}