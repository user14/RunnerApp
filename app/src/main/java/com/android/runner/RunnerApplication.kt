package com.android.runner

import android.app.Application
import coil.util.DebugLogger
import com.android.auth.data.di.authDataModule
import com.android.auth.presentation.di.authViewModelModule
import com.android.core.data.di.coreDataModule
import com.android.runner.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class RunnerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidLogger()
            androidContext(this@RunnerApplication)
            modules(
                authDataModule,
                authViewModelModule,
                appModule,
                coreDataModule

            )
        }
    }
}