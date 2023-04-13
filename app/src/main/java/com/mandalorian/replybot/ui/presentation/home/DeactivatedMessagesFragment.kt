package com.mandalorian.replybot.ui.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mandalorian.replybot.R
import com.mandalorian.replybot.databinding.FragmentDeactivatedMessagesBinding
import com.mandalorian.replybot.ui.presentation.base.BaseFragment
import com.mandalorian.replybot.ui.presentation.home.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeactivatedMessagesFragment : BaseFragment<FragmentDeactivatedMessagesBinding>() {
    override val viewModel: HomeViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.fragment_deactivated_messages
    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner
    }

    companion object {
        private var deactivatedMessagesInstance: DeactivatedMessagesFragment? = null
        fun getInstance(): DeactivatedMessagesFragment {
            if (deactivatedMessagesInstance == null) {
                deactivatedMessagesInstance = DeactivatedMessagesFragment()
            }
            return deactivatedMessagesInstance!!
        }
    }
}