package com.mandalorian.replybot.ui.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.mandalorian.replybot.R
import com.mandalorian.replybot.databinding.FragmentHomeBinding
import com.mandalorian.replybot.ui.presentation.adapter.HomeAdapter
import com.mandalorian.replybot.ui.presentation.base.BaseFragment
import com.mandalorian.replybot.ui.presentation.home.activatedMessages.ActivatedMessagesFragment
import com.mandalorian.replybot.ui.presentation.home.deactivatedMessages.DeactivatedMessagesFragment
import com.mandalorian.replybot.ui.presentation.home.viewModel.HomeViewModel
import com.mandalorian.replybot.ui.presentation.home.viewModel.HomeViewModelFactory

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val activatedFragment = ActivatedMessagesFragment.getInstance()
    private val deactivatedFragment = DeactivatedMessagesFragment.getInstance()
    override val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(navController)
    }
    override fun getLayoutResource() = R.layout.fragment_home

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)

        binding?.viewModel = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner

        setupAdapter()
        setupTabLayout()

        setFragmentResultListener("from_add_product") { _, result ->
            val refresh = result.getBoolean("refresh")
            if (refresh) {
                viewModel.onRefresh()
            }
        }

        setFragmentResultListener("from_update") { _, result ->
            val refresh = result.getBoolean("refresh")
            if (refresh) {
                viewModel.onRefresh()
            }
        }
    }

    private fun setupAdapter() {
        val adapter = HomeAdapter(
            listOf(activatedFragment, deactivatedFragment),
            childFragmentManager,
            lifecycle
        )
        binding?.viewPager?.adapter = adapter
    }

    private fun setupTabLayout() {
        //        val tabs = listOf("Activated", "Deactivated")
        binding?.let {
            TabLayoutMediator(it.tabLayout, it.viewPager) { tab, position ->
//                tab.text = tabs[position]
                when (position) {
                    0 -> tab.text = "Activated"
                    1 -> tab.text = "Deactivated"
                }
            }.attach()
        }
    }
}