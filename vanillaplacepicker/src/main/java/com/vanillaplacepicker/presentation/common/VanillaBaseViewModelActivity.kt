package com.vanillaplacepicker.presentation.common

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class VanillaBaseViewModelActivity<VB : ViewBinding, T : ViewModel> : AppCompatActivity() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    protected val viewModel by lazy { buildViewModel() }

    protected open fun getBundle() {
    }

    protected abstract fun buildViewModel(): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflateLayout(layoutInflater)
        setContentView(binding.root)
        getBundle()
        initViews()
        initLiveDataObservers()
    }

    abstract fun inflateLayout(layoutInflater: LayoutInflater): VB

    @CallSuper
    protected open fun initLiveDataObservers() = Unit

    @CallSuper
    protected open fun initViews() {
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}