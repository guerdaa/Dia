package com.android.dia.di.ui.add_measure

import androidx.lifecycle.ViewModel
import com.android.dia.di.ViewModelKey
import com.android.dia.ui.add_food.AddFoodViewModel
import com.android.dia.ui.add_measure.AddMeasureViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AddMeasureViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AddMeasureViewModel::class)
    abstract fun bindAddMeasureViewModel(viewModel: AddMeasureViewModel): ViewModel
}