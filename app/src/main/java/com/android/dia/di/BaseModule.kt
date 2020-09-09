package com.android.dia.di

import android.content.SharedPreferences
import androidx.fragment.app.FragmentActivity
import com.android.dia.BaseActivity
import com.android.dia.model.Settings
import com.android.dia.utils.Constants
import com.android.dia.utils.adapter.ViewPagerAdapter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Singleton

@Module
 class BaseModule {

    @BaseScope
    @Provides
    fun providePagerAdapter(activity: BaseActivity): ViewPagerAdapter {
        return ViewPagerAdapter(activity.window.decorView.rootView, activity.supportFragmentManager)
    }

}