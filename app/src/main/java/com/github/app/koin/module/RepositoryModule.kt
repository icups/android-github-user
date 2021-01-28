package com.github.app.koin.module

import com.github.app.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { UserRepository(get(), get()) }
}