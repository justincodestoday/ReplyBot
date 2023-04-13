package com.mandalorian.replybot.ui.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.mandalorian.replybot.R
import com.mandalorian.replybot.databinding.FragmentHomeBinding
import com.mandalorian.replybot.ui.presentation.adapter.HomeAdapter
import com.mandalorian.replybot.ui.presentation.base.BaseFragment
import com.mandalorian.replybot.ui.presentation.base.viewModel.BaseViewModel
import com.mandalorian.replybot.ui.presentation.home.viewModel.HomeViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val activatedFragment = ActivatedMessagesFragment.getInstance()
    private val deactivatedMessagesFragment = DeactivatedMessagesFragment.getInstance()

    override val viewModel: HomeViewModel by viewModels()

    override fun getLayoutResource() = R.layout.fragment_home

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentHomeBinding.inflate(inflater, container, false)
//        return binding.root
//    }

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)

        binding?.btnToCreate?.setOnClickListener {
            val action = HomeFragmentDirections.toCreateMessageFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        val adapter = HomeAdapter(
            listOf(activatedFragment, deactivatedMessagesFragment),
            childFragmentManager,
            lifecycle
        )
        binding?.viewPager?.adapter = adapter
    }
}