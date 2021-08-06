package com.example.retrofitep

import android.app.Application
import com.example.retrofitep.di.companyModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        setupTimber()
        setupKoinDI()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupKoinDI() {
        startKoin {
            androidContext(this@App)
            modules(
                companyModule
            )
        }
    }

}