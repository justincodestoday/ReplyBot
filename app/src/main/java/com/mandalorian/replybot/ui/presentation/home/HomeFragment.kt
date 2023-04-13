package com.mandalorian.replybot.ui.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.mandalorian.replybot.databinding.FragmentHomeBinding
import com.mandalorian.replybot.ui.presentation.adapter.HomeAdapter

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val activatedFragment = ActivatedMessagesFragment.getInstance()
    private val deactivatedFragment = DeactivatedMessagesFragment.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = HomeAdapter(
            listOf(activatedFragment, deactivatedFragment),
            childFragmentManager,
            lifecycle
        )
        binding.viewPager.adapter = adapter

        val tabs = listOf("Activated", "Deactivated")
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabs[position]
        }.attach()
    }
}