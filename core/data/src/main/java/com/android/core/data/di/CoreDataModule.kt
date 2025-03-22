package com.android.core.data.di

import com.android.core.data.networking.HttpClientFactory
import org.koin.dsl.module

val coreDataModule = module {
    single(){
        HttpClientFactory().build()
    }
}