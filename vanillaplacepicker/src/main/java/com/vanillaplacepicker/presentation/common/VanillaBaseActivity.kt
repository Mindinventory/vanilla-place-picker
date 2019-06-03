package com.vanillaplacepicker.presentation.common

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class VanillaBaseActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    protected abstract fun getContentResource(): Int

    protected open fun getBundle() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentResource())
        getBundle()
        initViews()
    }

    @CallSuper
    protected open fun initViews() {
    }

    protected fun Disposable.collect() = compositeDisposable.add(this)
}