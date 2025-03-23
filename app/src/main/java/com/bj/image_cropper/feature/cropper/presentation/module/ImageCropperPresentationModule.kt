package com.bj.image_cropper.feature.cropper.presentation.module

import com.bj.image_cropper.feature.cropper.presentation.controller.vm.ImageCropperViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val imageCropperPresentationModule = module {
    viewModelOf(::ImageCropperViewModel)
}