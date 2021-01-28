package com.github.app.base

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import org.koin.android.viewmodel.compat.ViewModelCompat.viewModel

abstract class BaseActivity<VM : ViewModel, VDB : ViewDataBinding>(private val viewModelClass: Class<VM>) : AppCompatActivity() {

    open val viewModel: VM by viewModel(this@BaseActivity, viewModelClass)
    lateinit var binding: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setPortraitOrientation()
        attachViewDataBinding()

        setupToolbar()
        setupView()

        setupAdapter()
        setupListener()
        setupObserver()

        onViewCreated()
        initAPI()
    }

    private fun setPortraitOrientation() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun attachViewDataBinding() {
        DataBindingUtil.setContentView<VDB>(this@BaseActivity, layoutResources()).apply {
            setFinishOnTouchOutside(false)
            binding = this
        }
    }

    protected abstract fun onViewCreated()

    protected open fun setupView() {}
    protected open fun setupAdapter() {}
    protected open fun setupToolbar() {}
    protected open fun setupListener() {}
    protected open fun setupObserver() {}

    protected open fun initAPI() {}

    @LayoutRes
    protected abstract fun layoutResources(): Int

}