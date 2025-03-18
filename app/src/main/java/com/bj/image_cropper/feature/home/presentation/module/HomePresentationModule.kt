package com.bj.image_cropper.feature.home.presentation.module

import com.bj.image_cropper.feature.home.presentation.controller.vm.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val homePresentationModule =  module {
    viewModelOf(::HomeViewModel)
}