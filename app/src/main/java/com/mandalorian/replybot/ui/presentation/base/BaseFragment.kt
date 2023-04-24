package com.mandalorian.replybot.ui.presentation.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.mandalorian.replybot.ui.presentation.base.viewModel.BaseViewModel
import kotlinx.coroutines.launch

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    lateinit var navController: NavController
    var binding: T? = null

    abstract val viewModel: BaseViewModel
    abstract fun getLayoutResource(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.onViewCreated()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResource(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)
        onBindView(view, savedInstanceState)
        onBindData(view)
    }

    open fun onBindView(view: View, savedInstanceState: Bundle?) {
        binding = DataBindingUtil.bind(view)
        binding?.lifecycleOwner = viewLifecycleOwner
        lifecycleScope.launch {
            viewModel.error.collect {
                val snackbar = Snackbar.make(view, it, Snackbar.LENGTH_LONG)
                snackbar.show()
            }
        }
    }

    open fun onBindData(view: View) {}
}