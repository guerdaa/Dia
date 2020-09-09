package com.android.dia.di

import android.app.Application
import com.android.dia.Sugapp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component (
    modules = [
    AndroidSupportInjectionModule::class,
    ActivityBuilderModule::class,
    FragmentBuilderModule::class,
    AppModule::class,
    ViewModelProviderFactoryModule::class
    ]


)
interface AppComponent : AndroidInjector<Sugapp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application (application: Application): Builder
        fun build() : AppComponent
    }
}