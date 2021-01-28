package com.github.app.koin.source

import com.github.services.UserServices
import org.koin.dsl.module
import retrofit2.Retrofit

val remoteDataSource = module {
    factory { provideUserServices(get()) }
}

fun provideUserServices(retrofit: Retrofit): UserServices {
    return retrofit.create(UserServices::class.java)
}