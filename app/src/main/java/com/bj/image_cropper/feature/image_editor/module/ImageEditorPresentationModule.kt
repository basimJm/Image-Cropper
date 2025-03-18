package com.bj.image_cropper.feature.image_editor.module

import com.bj.image_cropper.feature.image_editor.controller.vm.ImageEditorViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val imageEditorPresentationModule = module {
    viewModelOf(::ImageEditorViewModel)
}