package com.android.dia.di.ui.foods

import androidx.lifecycle.ViewModel
import com.android.dia.di.ViewModelKey
import com.android.dia.ui.add_food.AddFoodViewModel
import com.android.dia.ui.foods.FoodsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FoodsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FoodsViewModel::class)
    abstract fun bindFoodsViewModel(viewModel: FoodsViewModel): ViewModel
}