package com.github.app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.app.repository.UserRepository
import com.github.model.User
import kotlinx.coroutines.launch

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

    private val mUsers = MutableLiveData<Result<List<User>>>()
    val users: LiveData<Result<List<User>>> = mUsers

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    var lastKeyword = ""
    var nextPage = 1

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    fun findUser(keyword: String = lastKeyword, page: Int = nextPage) {
        viewModelScope.launch {
            if (page == 1) mUiMode.postValue(UiMode.ON_PROGRESS)
            try {
                repository.findUser(keyword, page, PER_PAGE).run {
                    mUsers.postValue(Result.success(result))
                    mUiMode.postValue(if (totalCount > 0) UiMode.SUCCESS else UiMode.NOT_FOUND)
                    lastKeyword = keyword
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                mUsers.postValue(Result.failure(ex))
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

}