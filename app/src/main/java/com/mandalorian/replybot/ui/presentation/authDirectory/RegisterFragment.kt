package com.mandalorian.replybot.ui.presentation.authDirectory

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.mandalorian.replybot.R
import com.mandalorian.replybot.databinding.FragmentRegisterBinding
import com.mandalorian.replybot.ui.presentation.authDirectory.viewModel.registerViewModel
import com.mandalorian.replybot.ui.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    override val viewModel: registerViewModel by viewModels()

    override fun getLayoutResource() = R.layout.fragment_register

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)

        binding?.run {
            btnRegister.setOnClickListener {

                val username = binding?.etUsername?.text.toString()
                val password = binding?.etPassword?.text.toString()
                val conPass = binding?.etConfirmPassword?.text.toString()
                val email = binding?.etEmail?.text.toString()

                if (username.length < 2 && password.length < 6) {
                    val snackBar =
                        Snackbar.make(
                            binding!!.root,
                            "Please enter all the values",
                            Snackbar.LENGTH_LONG
                        )
                    snackBar.show()
                } else {
                    lifecycleScope.launch {
                        Log.d("debugging", "Is it even entering here?")
                        viewModel.register(username, email, password, conPass)
                    }
                }
            }

            tvToLogin.setOnClickListener {
                navController.navigate(R.id.loginFragment)
            }
        }
    }


    override fun onBindData(view: View) {
        super.onBindData(view)
        lifecycleScope.launch {
            viewModel.finish.collect {
                val action = RegisterFragmentDirections.actionRegisterToLogin()
                navController.navigate(action)
            }
        }
    }
}