package com.github.app.ui.main

import android.content.Context
import android.content.Intent
import com.github.app.R
import com.github.app.base.BaseActivity
import com.github.app.databinding.ActivityMainBinding
import com.github.app.listener.LinearLoadMoreListener
import com.github.app.ui.dialog.DialogConfirmation
import com.github.app.ui.main.MainViewModel.UiMode
import com.github.app.ui.main.MainViewModel.UiRequest
import com.github.app.ui.user.UserAdapter
import com.github.ext.alert.showLongToast
import com.github.ext.alert.showToast
import com.github.ext.common.exitFromApps
import com.github.ext.input.hideKeyboard
import com.github.ext.vm.observe
import com.github.model.State
import com.github.model.User
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class.java) {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun layoutResources() = R.layout.activity_main

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    private val adapter = UserAdapter()

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    private var onProcess: Boolean = false
    private var errorTimer: Timer? = null

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    override fun onViewCreated() {
        binding.apply {
            lifecycleOwner = this@MainActivity
            vm = viewModel
        }
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    override fun setupObserver() {
        viewModel.run {
            observe(uiMode) {
                when (it) {
                    UiMode.INITIATE -> adapter.clear()
                    else -> return@observe
                }
            }

            observe(uiRequest) {
                when (it) {
                    UiRequest.FIND_USER -> doFindUser()
                }
            }

            // Sorry for the duplicate item in list, probably isn't adapter fault, github api returning the same data on a different page.
            observe(users) { result ->
                when (result) {
                    is State.Success -> handleUserData(result.data)
                    is State.Failure -> handleException(result.message)
                    is State.Error -> handleException(result.message)
                }; onProcess = false
            }
        }
    }

    override fun setupAdapter() {
        binding.recyclerUser.adapter = adapter
    }

    override fun setupListener() {
        binding.recyclerUser.run {
            addOnScrollListener(object : LinearLoadMoreListener(layoutManager) {
                override fun isLoading(): Boolean {
                    return onProcess
                }

                override fun loadMoreItems() {
                    if (errorTimer == null) viewModel.findUser(); onProcess = true
                }
            })
        }
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    private fun MainViewModel.doFindUser() {
        binding.editKeyword.hideKeyboard()

        when {
            requireKeyword().isEmpty() -> viewModel.initiateLayout()
            errorTimer != null -> showToast("Please try again later...")
            else -> {
                findUser(requireKeyword(), 1); mPage = 1; onProcess = true;
            }
        }
    }

    private fun MainViewModel.handleUserData(list: List<User>) {
        if (list.isNotEmpty()) {
            adapter.run { if (mPage == 1) replaceAll(list) else addAll(list) }; mPage++
        }
    }

    private fun handleException(message: String?) {
        showLongToast(message)
        onProcess = true
        errorTimer?.cancel()
        errorTimer = Timer().apply {
            schedule(15000) { errorTimer = null; onProcess = false }
        }
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    private fun requireKeyword(): String {
        return binding.editKeyword.text.toString()
    }

    override fun onBackPressed() {
        DialogConfirmation(this@MainActivity) { exitFromApps() }
    }

}