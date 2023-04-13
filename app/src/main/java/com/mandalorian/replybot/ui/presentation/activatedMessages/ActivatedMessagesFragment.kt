package com.mandalorian.replybot.ui.presentation.activatedMessages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mandalorian.replybot.R
import com.mandalorian.replybot.databinding.FragmentActivatedMessagesBinding
import com.mandalorian.replybot.ui.presentation.activatedMessages.viewModel.ActivatedMessagesViewModel
import com.mandalorian.replybot.ui.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivatedMessagesFragment : BaseFragment<FragmentActivatedMessagesBinding>() {
    override val viewModel: ActivatedMessagesViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.fragment_activated_messages

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activated_messages, container, false)
    }

}