package com.mandalorian.replybot.ui.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mandalorian.replybot.R
import com.mandalorian.replybot.databinding.FragmentActivatedMessagesBinding
import com.mandalorian.replybot.ui.presentation.base.BaseFragment
import com.mandalorian.replybot.ui.presentation.home.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivatedMessagesFragment : BaseFragment<FragmentActivatedMessagesBinding>() {
    override val viewModel: HomeViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.fragment_activated_messages

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner
    }

    companion object {
        private var activatedMessagesInstance: ActivatedMessagesFragment? = null
        fun getInstance(): ActivatedMessagesFragment {
            if (activatedMessagesInstance == null) {
                activatedMessagesInstance = ActivatedMessagesFragment()
            }
            return activatedMessagesInstance!!
        }
    }
}