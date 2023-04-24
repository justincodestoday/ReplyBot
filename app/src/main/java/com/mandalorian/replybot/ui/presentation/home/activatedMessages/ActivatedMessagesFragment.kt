package com.mandalorian.replybot.ui.presentation.home.activatedMessages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mandalorian.replybot.R
import com.mandalorian.replybot.databinding.FragmentActivatedMessagesBinding
import com.mandalorian.replybot.ui.presentation.adapter.MessagesAdapter
import com.mandalorian.replybot.ui.presentation.base.BaseFragment
import com.mandalorian.replybot.ui.presentation.home.HomeFragmentDirections
import com.mandalorian.replybot.ui.presentation.home.activatedMessages.viewModel.ActivatedMessagesViewModel
import com.mandalorian.replybot.ui.presentation.home.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActivatedMessagesFragment : BaseFragment<FragmentActivatedMessagesBinding>() {
    override val viewModel: ActivatedMessagesViewModel by viewModels()
    private val parentViewModel: HomeViewModel by viewModels(
        ownerProducer = {
            requireParentFragment()
        }
    )
    private lateinit var adapter: MessagesAdapter
    override fun getLayoutResource(): Int = R.layout.fragment_activated_messages

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
            val action = HomeFragmentDirections.actionHomeFragmentToUpdateMessageFragment(it.id!!)
            NavHostFragment.findNavController(this).navigate(action)
        }
        binding?.rvMessages?.adapter = adapter
        binding?.rvMessages?.layoutManager = layoutManager
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