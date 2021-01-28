package com.github.app.base

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<VDB : ViewDataBinding>() : Fragment() {

    lateinit var binding: VDB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResources(), container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupListener()
        setupFragmentObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewPager()

        onBaseViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        onBaseResume()
    }

    fun terminate(setResult: Boolean = false, resultCode: Int = RESULT_OK) {
        if (setResult) {
            requireActivity().setResult(resultCode)
        }

        requireActivity().finish()
    }

    protected open fun onBaseViewCreated(view: View, savedInstanceState: Bundle?) {}
    protected open fun onBaseResume() {}

    protected open fun initView() {}
    protected open fun initViewPager() {}

    protected open fun setupListener() {}
    protected open fun setupFragmentObserver() {}

    protected abstract fun layoutResources(): Int

}