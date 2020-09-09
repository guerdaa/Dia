package com.android.dia.di

import com.android.dia.di.ui.add_food.AddFoodScope
import com.android.dia.di.ui.add_food.AddFoodViewModelModule
import com.android.dia.di.ui.add_measure.AddMeasureScope
import com.android.dia.di.ui.add_measure.AddMeasureViewModelModule
import com.android.dia.di.ui.foods.FoodsScope
import com.android.dia.di.ui.foods.FoodsViewModelModule
import com.android.dia.di.ui.measures.MeasuresScope
import com.android.dia.di.ui.measures.MeasuresViewModelModule
import com.android.dia.di.ui.settings.SettingsScope
import com.android.dia.di.ui.settings.SettingsViewModelModule
import com.android.dia.ui.HelpDialogFragment
import com.android.dia.ui.InjectionDialogFragment
import com.android.dia.ui.add_food.AddFoodFragment
import com.android.dia.ui.add_food.AddFoodViewModel
import com.android.dia.ui.add_measure.AddMeasureFragment
import com.android.dia.ui.add_measure.AddMeasureViewModel
import com.android.dia.ui.foods.FoodsFragment
import com.android.dia.ui.foods.FoodsViewModel
import com.android.dia.ui.measures.MeasuresFragment
import com.android.dia.ui.measures.MeasuresViewModel
import com.android.dia.ui.settings.SettingsFragment
import com.android.dia.ui.settings.SettingsViewModel
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector(modules = [SettingsViewModelModule::class])
    abstract fun provideSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector(modules = [AddFoodViewModelModule::class])
    abstract fun provideAddFoodFragment(): AddFoodFragment

    @ContributesAndroidInjector(modules = [AddMeasureViewModelModule::class])
    abstract fun provideAddMeasureFragment(): AddMeasureFragment

    @ContributesAndroidInjector(modules = [MeasuresViewModelModule::class])
    abstract fun provideMeasuresFragment(): MeasuresFragment

    @ContributesAndroidInjector(modules = [FoodsViewModelModule::class])
    abstract fun provideFoodsFragment(): FoodsFragment

    @ContributesAndroidInjector
    abstract fun provideInjectionDialogFragment(): InjectionDialogFragment

    @ContributesAndroidInjector
    abstract fun provideHelpDialogFragment(): HelpDialogFragment

}