package com.github.app.ui.main

import android.app.Application
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.app.base.BaseViewModel
import com.github.app.constant.Page
import com.github.app.repository.UserRepository
import com.github.ext.gson.toJson
import com.github.ext.log.logError
import com.github.ext.log.logInfo
import com.github.network.model.State
import com.github.network.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val context: Application, private val repository: UserRepository) : BaseViewModel() {

    enum class UiRequest { USER }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    data class Parcel(val view: View? = null, val user: User? = null)

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    private val mUiRequest = MutableLiveData<Pair<UiRequest, Parcel>>()
    val uiRequest: LiveData<Pair<UiRequest, Parcel>> = mUiRequest

    private val mUsers = MutableLiveData<State<List<User>>>()
    val users: LiveData<State<List<User>>> get() = mUsers

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    fun fetchUsers(page: Int = Page.FIRST) {
        viewModelScope.launch {
            mUsers.postValue(State.loading())
            try {
                repository.getUsers(page).let {
                    mUsers.postValue(State.success(it))
                    logInfo(it.toJson(), "getUsersSuccess")
                }
            } catch (e: Exception) {
                mUsers.postValue(State.error(requireErrorMessage(context, e)))
                logError(e, "getUsersFailure")
            }
        }
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    fun clickLoyalty(data: User? = null) {
        mUiRequest.postValue(UiRequest.USER to Parcel(user = data))
    }

}