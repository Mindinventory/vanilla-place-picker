package com.vanillaplacepicker.presentation.common

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel

abstract class VanillaBaseViewModelActivity<T : ViewModel>:AppCompatActivity(){

    protected val viewModel by lazy { buildViewModel() }

    protected abstract fun getContentResource(): Int

    protected open fun getBundle() {
    }


    protected abstract fun buildViewModel(): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentResource())
        getBundle()
        initViews()
        initLiveDataObservers()
    }

    @CallSuper
    protected open fun initLiveDataObservers() = Unit

    @CallSuper
    protected open fun initViews() {
    }
}