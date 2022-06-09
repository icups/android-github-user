package com.github.app.ui.main

import com.github.app.R
import com.github.app.base.BaseActivity
import com.github.app.constant.Millis
import com.github.app.databinding.ActivityMainBinding
import com.github.app.listener.LinearLoadMoreListener
import com.github.app.ui.main.MainViewModel.UiRequest
import com.github.app.ui.user.UserAdapter
import com.github.ext.alert.showToast
import com.github.ext.common.launchDelayedFunction
import com.github.ext.refresh.finish
import com.github.ext.view.hide
import com.github.ext.view.reveal
import com.github.network.model.Status
import com.github.network.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class.java) {

    private lateinit var adapter: UserAdapter

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    private var mPage: Int = 1

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    override fun onViewCreated() {
        binding.run {
            lifecycleOwner = this@MainActivity
            error = false

            setupLoading(progressCircular)
        }
    }

    override fun layoutResource(): Int {
        return R.layout.activity_main
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    override fun onBackPressed() {
        finishAffinity()
    }

    override fun initAPI() {
        viewModel.fetchUsers()
    }

    override fun setupObserver() {
        viewModel.run {
            uiRequest.observe(this@MainActivity) {
                when (it.first) {
                    UiRequest.USER -> it.second.user?.run { showToast("Username : $username") }
                }
            }

            users.observe(this@MainActivity) {
                when (it.status) {
                    Status.LOADING -> showLoading { binding.error = false }
                    Status.SUCCESS -> hideLoading { it.data?.collect() }
                    Status.ERROR -> hideLoading {
                        if (adapter.isEmpty()) binding.error = true
                        showToast(it.message)
                    }
                }
            }
        }
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    override fun setupAdapter() {
        adapter = UserAdapter(viewModel)
        binding.recyclerUser.adapter = adapter
    }

    override fun setupListener() {
        binding.run {
            swiperUser.setOnRefreshListener {
                setupLoading(progressCircular)
                swiperUser.finish(Millis.HALF_SECOND)

                initAPI()
            }

            recyclerUser.run {
                addOnScrollListener(object : LinearLoadMoreListener(layoutManager) {
                    override fun isLoading(): Boolean {
                        return onProcess
                    }

                    override fun loadMoreItems() {
                        progressCircular.hide()
                        setupLoading(progressMore)

                        viewModel.fetchUsers(mPage)
                        onProcess = true
                    }
                })
            }
        }
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    private fun List<User>.collect() {
        if (adapter.isNotEmpty()) {
            adapter.addAll(this.filter { !adapter.getList().contains(it) })
        } else {
            adapter.initialize(this)
        }

        binding.run {
            progressCircular.hide()
            launchDelayedFunction(Millis.HALF_SECOND) { swiperUser.reveal() }
        }

        mPage *= 11
        onProcess = false
    }

}