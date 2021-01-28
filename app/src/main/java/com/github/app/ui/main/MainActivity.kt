package com.github.app.ui.main

import android.content.Context
import android.content.Intent
import com.github.app.R
import com.github.app.base.BaseActivity
import com.github.app.databinding.ActivityMainBinding
import com.github.app.listener.LinearLoadMoreListener
import com.github.app.ui.dialog.DialogConfirmation
import com.github.app.ui.main.MainViewModel.UiRequest
import com.github.app.ui.user.UserAdapter
import com.github.ext.alert.showLongToast
import com.github.ext.alert.showToast
import com.github.ext.common.exitFromApps
import com.github.ext.gson.fromJson
import com.github.ext.input.hideKeyboard
import com.github.ext.vm.observe
import com.github.response.ErrorResponse
import retrofit2.HttpException
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

    private var loading: Boolean = false
    private var errorTimer: Timer? = null

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    override fun onViewCreated() {
        binding.apply {
            lifecycleOwner = this@MainActivity
            vm = viewModel
        }
    }

    override fun setupObserver() {
        viewModel.run {
            observe(uiRequest) {
                when (it) {
                    UiRequest.FIND_USER -> doFindUser()
                }
            }

            // Sorry for the duplicate item in list, probably isn't adapter fault, github api returning the same data on a different page.
            observe(users) {
                it.onSuccess { result ->
                    if (result.isEmpty()) return@observe
                    else adapter.run { if (nextPage == 1) replaceAll(result) else addAll(result) }; nextPage++
                }
                it.onFailure { ex ->
                    if (ex is HttpException) {
                        val response = fromJson(ex.response()?.errorBody()?.string(), ErrorResponse::class.java)
                        showErrorMessage(response?.message)
                    } else {
                        showErrorMessage(ex.message)
                    }
                }; loading = false
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
                    return loading
                }

                override fun loadMoreItems() {
                    if (errorTimer == null) viewModel.findUser(); loading = true
                }
            })
        }
    }

    private fun doFindUser() {
        binding.editKeyword.hideKeyboard()

        if (requireKeyword().isEmpty()) {
            viewModel.initiateLayout(); adapter.clear(); return
        }

        if (errorTimer != null) {
            showToast("Please try again later..."); return
        } else {
            viewModel.run { findUser(requireKeyword(), 1); nextPage = 1 }; loading = true;
        }
    }

    private fun showErrorMessage(message: String?) {
        showLongToast(message)

        errorTimer?.cancel()
        errorTimer = Timer().apply {
            schedule(15000) { errorTimer = null; loading = false }
        }
    }

    private fun requireKeyword(): String {
        return binding.editKeyword.text.toString()
    }

    override fun onBackPressed() {
        DialogConfirmation(this@MainActivity) { exitFromApps() }
    }

}