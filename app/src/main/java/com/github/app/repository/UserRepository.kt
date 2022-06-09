package com.github.app.repository

import com.github.app.constant.Page
import com.github.network.model.User
import com.github.network.service.UserService
import javax.inject.Inject

class UserRepository @Inject constructor(private val remoteDataSource: UserService) {

    suspend fun getUsers(page: Int): List<User> {
        return remoteDataSource.getUsers(page, Page.DEFAULT_SIZE)
    }

}