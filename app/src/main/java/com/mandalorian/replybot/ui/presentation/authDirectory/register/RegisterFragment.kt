package com.mandalorian.replybot.ui.presentation.authDirectory.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mandalorian.replybot.R
import com.mandalorian.replybot.databinding.FragmentRegisterBinding
import com.mandalorian.replybot.ui.presentation.authDirectory.register.viewModel.RegisterViewModel
import com.mandalorian.replybot.ui.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    override val viewModel: RegisterViewModel by viewModels()
    override fun getLayoutResource() = R.layout.fragment_register

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        (activity as? AppCompatActivity)?.supportActionBar?.hide()
//        (activity as? MainActivity)?.setDrawerEnabled(false)
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        (activity as? AppCompatActivity)?.supportActionBar?.show()
//        (activity as? MainActivity)?.setDrawerEnabled(true)
//    }

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner

        binding?.run {
            tvToLogin.setOnClickListener {
                navController.popBackStack()
            }
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        lifecycleScope.launch {
            viewModel.finish.collect {
                navController.popBackStack()
            }
        }
    }
}