package com.github.app.koin.source

import com.github.database.UserDatabase
import org.koin.dsl.module

val localDataSourceModule = module {
    factory { UserDatabase.getInstance(get()) }
}