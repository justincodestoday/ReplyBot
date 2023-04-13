package com.mandalorian.replybot.ui.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mandalorian.replybot.databinding.FragmentHomeBinding
import com.mandalorian.replybot.ui.presentation.adapter.HomeAdapter

class HomeFragment : Fragment() {
private lateinit var binding: FragmentHomeBinding
    private var param2: String? = null
    private lateinit var binding: FragmentHomeBinding
    private val activatedFragment = ActivatedMessagesFragment.getInstance()
    private val deactivatedMessagesFragment = DeactivatedMessagesFragment.getInstance()

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
            listOf(activatedFragment, deactivatedMessagesFragment),
            childFragmentManager,
            lifecycle
        )
        binding.viewPager.adapter = adapter
    }
}