package com.mandalorian.replybot.ui.presentation.home.deactivatedMessages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mandalorian.replybot.R
import com.mandalorian.replybot.databinding.FragmentDeactivatedMessagesBinding
import com.mandalorian.replybot.ui.presentation.adapter.MessagesAdapter
import com.mandalorian.replybot.ui.presentation.base.BaseFragment
import com.mandalorian.replybot.ui.presentation.home.HomeFragmentDirections
import com.mandalorian.replybot.ui.presentation.home.deactivatedMessages.viewModel.DeactivatedMessageViewModel
import com.mandalorian.replybot.ui.presentation.home.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DeactivatedMessagesFragment : BaseFragment<FragmentDeactivatedMessagesBinding>() {
    override val viewModel: DeactivatedMessageViewModel by viewModels()
    private val parentViewModel: HomeViewModel by viewModels(
        ownerProducer = {
            requireParentFragment()
        }
    )
    private lateinit var adapter: MessagesAdapter
    override fun getLayoutResource(): Int = R.layout.fragment_deactivated_messages
    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)
//        binding?.viewModel = viewModel
//        binding?.lifecycleOwner = viewLifecycleOwner

        setupAdapter()
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        viewModel.messages.observe(viewLifecycleOwner) {
            adapter.setMessage(it)
        }

        lifecycleScope.launch {
            parentViewModel.refresh.collect {
                viewModel.getMessages()
            }
        }
    }

    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = MessagesAdapter(mutableListOf()) {
            val action = HomeFragmentDirections.actionHomeFragmentToEditMessageFragment(it.id!!)
            NavHostFragment.findNavController(this).navigate(action)
        }
        binding?.rvMessages?.adapter = adapter
        binding?.rvMessages?.layoutManager = layoutManager
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