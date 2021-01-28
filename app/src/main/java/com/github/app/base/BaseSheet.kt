package com.github.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.github.app.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseSheet<VDB : ViewDataBinding> : BottomSheetDialogFragment() {

    lateinit var mViewDataBinding: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NORMAL, dialogStyle())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, layoutResources(), container, false)

        return mViewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupListener()
        setupFragmentObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initAdapter()

        setupAdapter()

        onBaseViewCreated(view, savedInstanceState)
    }

    protected open fun onBaseViewCreated(view: View, savedInstanceState: Bundle?) {}

    protected open fun setupAdapter() {}

    protected open fun setupListener() {}
    protected open fun setupFragmentObserver() {}

    protected open fun initView() {}

    protected open fun initViewPager() {}
    protected open fun initAdapter() {}

    protected open fun initAPI() {}

    protected abstract fun layoutResources(): Int

    protected open fun dialogStyle(): Int {
        return R.style.AppTheme_Sheet
    }

}