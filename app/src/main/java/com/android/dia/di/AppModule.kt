package com.android.dia.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.android.dia.model.Settings
import com.android.dia.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideFirestoreFirebase(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences(Constants.SHARED_PREF, MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideSharedPreferencesEditor(sharedPreferences: SharedPreferences): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }

    @Singleton
    @Provides
    fun provideSettings(sharedPreferences: SharedPreferences): Settings {
        val morning = sharedPreferences.getInt(Constants.MORNING_FACTOR_PREF, 8)
        val afternoon = sharedPreferences.getInt(Constants.AFTERNOON_FACTOR_PREF, 8)
        val night = sharedPreferences.getInt(Constants.NIGHT_FACTOR_PREF, 8)

        val lantusAlarm = sharedPreferences.getString(Constants.LANTUS_ALARM_PREF, "22:00")
        val lantusUnits = sharedPreferences.getInt(Constants.LANTUS_UNITS_PREF, 10)
        val measureAlarm = sharedPreferences.getInt(Constants.MEASURE_ALARM_PREF, 2)

        return Settings(morning, afternoon, night, lantusUnits, lantusAlarm!!, measureAlarm)
    }
}