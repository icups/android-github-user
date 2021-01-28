package com.github.app.repository

import com.github.database.UserDatabase
import com.github.model.User
import com.github.response.PaginatedResponse
import com.github.services.UserServices

class UserRepository(private val remoteDataSource: UserServices, private val localDataSource: UserDatabase) {

    suspend fun findUser(keyword: String, page: Int, perPage: Int): PaginatedResponse<User> {
        return remoteDataSource.findUser(keyword, page, perPage)
    }

}