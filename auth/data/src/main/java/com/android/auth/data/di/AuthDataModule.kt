package com.android.auth.data.di

import com.android.auth.data.AuthRepositoryImpl
import com.android.auth.data.EmailValidator
import com.android.auth.domain.AuthRepository
import com.android.auth.domain.PatternValidator
import com.android.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> {
        EmailValidator
    }
    singleOf(::UserDataValidator)
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
}