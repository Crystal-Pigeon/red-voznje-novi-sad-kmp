package org.kmp

import org.kmp.experiment.TestViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { TestViewModel(get(), get()) }
}