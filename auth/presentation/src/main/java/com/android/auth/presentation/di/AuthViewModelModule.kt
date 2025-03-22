package com.android.auth.presentation.di

import com.android.auth.presentation.resigter.RegisterViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authViewModelModule = module {
    singleOf(::RegisterViewModel)
}