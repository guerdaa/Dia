package com.android.dia.di.ui.settings

import androidx.lifecycle.ViewModel
import com.android.dia.di.ViewModelKey
import com.android.dia.ui.add_food.AddFoodViewModel
import com.android.dia.ui.settings.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SettingsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel
}