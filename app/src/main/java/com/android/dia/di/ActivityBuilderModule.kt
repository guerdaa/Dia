package com.android.dia.di

import com.android.dia.BaseActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @BaseScope
    @ContributesAndroidInjector(modules = [BaseModule::class])
    abstract fun contributeBasicActivity(): BaseActivity
}