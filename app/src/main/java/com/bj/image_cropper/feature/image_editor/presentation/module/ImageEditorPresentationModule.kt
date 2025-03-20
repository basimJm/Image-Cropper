package com.bj.image_cropper.feature.image_editor.presentation.module

import com.bj.image_cropper.feature.image_editor.presentation.controller.vm.ImageEditorViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val imageEditorPresentationModule = module {
    viewModelOf(::ImageEditorViewModel)
}