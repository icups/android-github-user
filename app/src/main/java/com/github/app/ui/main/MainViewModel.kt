package com.github.app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.app.repository.UserRepository
import com.github.exception.NetworkException
import com.github.model.State
import com.github.model.User
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    companion object {
        private const val PER_PAGE = 15
    }

    enum class UiRequest { FIND_USER }
    enum class UiMode { INITIATE, ON_PROGRESS, NOT_FOUND, SUCCESS, ERROR }

    private val mUiRequest = MutableLiveData<UiRequest>()
    val uiRequest: LiveData<UiRequest> = mUiRequest

    private val mUiMode = MutableLiveData<UiMode>()
    val uiMode: LiveData<UiMode> = mUiMode

    private val mUsers = MutableLiveData<State<List<User>>>()
    val users: LiveData<State<List<User>>> = mUsers

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    private var mKeyword = ""
    var mPage = 1

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    fun findUser(keyword: String = mKeyword, page: Int = mPage) {
        viewModelScope.launch {
            if (page == 1) mUiMode.postValue(UiMode.ON_PROGRESS)
            try {
                repository.findUser(keyword, page, PER_PAGE).run {
                    mUsers.postValue(State.Success(result))
                    mUiMode.postValue(if (totalCount > 0) UiMode.SUCCESS else UiMode.NOT_FOUND)
                    assignRecentKeyword(keyword)
                }
            } catch (e: Exception) {
                mUsers.postValue(when (e) {
                    is NetworkException -> State.Failure(e)
                    is UnknownHostException -> State.Error("Please check your internet connection and try again.")
                    else -> State.Error(e.message)
                })
                mUiMode.postValue(UiMode.ERROR)
            }
        }
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    fun initiateLayout() {
        mUiMode.postValue(UiMode.INITIATE)
    }

    fun clickFindUser() {
        mUiRequest.postValue(UiRequest.FIND_USER)
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    private fun assignRecentKeyword(data: String) {
        mKeyword = data
    }

}