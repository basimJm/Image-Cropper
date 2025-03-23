package com.bj.image_cropper.app

import android.app.Application
import com.bj.image_cropper.feature.home.presentation.module.homePresentationModule
import com.bj.image_cropper.feature.cropper.presentation.module.imageCropperPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                listOf(
                    homePresentationModule,
                    imageCropperPresentationModule
                )
            )
        }
    }
}