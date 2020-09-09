package com.android.dia.di.ui.add_food

import androidx.lifecycle.ViewModel
import com.android.dia.di.ViewModelKey
import com.android.dia.ui.add_food.AddFoodViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AddFoodViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AddFoodViewModel::class)
    abstract fun bindAddFoodViewModel(viewModel: AddFoodViewModel): ViewModel
}