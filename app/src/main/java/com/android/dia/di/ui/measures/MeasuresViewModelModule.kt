package com.android.dia.di.ui.measures

import androidx.lifecycle.ViewModel
import com.android.dia.di.ViewModelKey
import com.android.dia.ui.add_food.AddFoodViewModel
import com.android.dia.ui.measures.MeasuresViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MeasuresViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MeasuresViewModel::class)
    abstract fun bindMeasuresViewModel(viewModel: MeasuresViewModel): ViewModel
}