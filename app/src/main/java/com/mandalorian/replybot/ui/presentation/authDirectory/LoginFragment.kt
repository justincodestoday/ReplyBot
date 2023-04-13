package com.mandalorian.replybot.ui.presentation.authDirectory


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mandalorian.replybot.R
import com.mandalorian.replybot.databinding.FragmentLoginBinding
import com.mandalorian.replybot.ui.MainActivity
import com.mandalorian.replybot.ui.presentation.authDirectory.viewModel.loginViewModel
import com.mandalorian.replybot.ui.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val viewModel :loginViewModel by viewModels()
    override fun getLayoutResource() = R.layout.fragment_login

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_login, container, false)
//    }

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)

        binding?.run {
            btnLogin.setOnClickListener {
                val email = binding?.etEmail?.text.toString()
                val password = binding?.etPassword?.text.toString()
                lifecycleScope.launch {
                    viewModel.login(email,password)

                }
            }
            btnToRegister.setOnClickListener {
                val action = LoginFragmentDirections.actionLoginToRegister()
                navController.navigate(action)
            }
        }
    }


    override fun onBindData(view: View) {
        super.onBindData(view)
        lifecycleScope.launch {
            viewModel.loginFinish.collect {
                val action = LoginFragmentDirections.actionLoginToHome()
                navController.navigate(action)
//                (requireActivity() as MainActivity).onLogin()
//                NavHostFragment.findNavController(this@SignInFragment).navigate(action)
            }
        }

    }
}