package com.weather.foundation.dialog

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
abstract class ProgressDialogModule {

    @Binds
    abstract fun bindProgressDialog(
        progressDialogImpl: ProgressDialogImpl
    ): ProgressDialog
}
