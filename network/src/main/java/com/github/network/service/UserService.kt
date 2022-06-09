package com.github.network.service

import com.github.network.model.Request
import com.github.network.model.User
import com.github.network.response.PaginatedResponse
import com.github.network.response.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    @GET("users")
    suspend fun getUsers(
        @Query("since") page: Int,
        @Query("per_page") perPage: Int
    ): List<User>

}