package com.mandalorian.replybot.ui.presentation.messageForm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mandalorian.replybot.R
import com.mandalorian.replybot.databinding.MessageFormBinding
import com.mandalorian.replybot.ui.presentation.base.BaseFragment
import com.mandalorian.replybot.ui.presentation.messageForm.viewModel.MessageFormViewModel

class EditMessageFragment : BaseFragment<MessageFormBinding>() {
    override val viewModel: MessageFormViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.message_form
    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner
    }
}