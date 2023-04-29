package com.mandalorian.replybot.ui.presentation.authDirectory.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mandalorian.replybot.R
import com.mandalorian.replybot.databinding.FragmentLoginBinding
import com.mandalorian.replybot.ui.presentation.authDirectory.login.viewModel.LoginViewModel
import com.mandalorian.replybot.ui.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val viewModel: LoginViewModel by viewModels()
    override fun getLayoutResource() = R.layout.fragment_login

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

        binding?.btnToRegister?.setOnClickListener {
            navigateToRegister()
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)

        lifecycleScope.launch {
            viewModel.loginFinish.collect {
                navigateToHome()
            }
        }
    }

    private fun navigateToHome() {
        val action = LoginFragmentDirections.actionLoginToHome()
        navController.navigate(action)
    }

    private fun navigateToRegister() {
        val action = LoginFragmentDirections.actionLoginToRegister()
        navController.navigate(action)
    }
}