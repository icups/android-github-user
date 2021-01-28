package com.github.services

import com.github.model.User
import com.github.response.PaginatedResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserServices {

    @GET("search/users")
    suspend fun findUser(
        @Query("q") keyword: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): PaginatedResponse<User>

}