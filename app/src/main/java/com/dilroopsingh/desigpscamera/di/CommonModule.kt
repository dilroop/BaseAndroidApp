package com.dilroopsingh.desigpscamera.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.dilroopsingh.desigpscamera.preferences.Preferences
import com.dilroopsingh.desigpscamera.preferences.Prefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CommonModule {

    @Provides
    fun providesContext(application: Application): Context = application.applicationContext

    @Provides
    fun provideResources(context: Context): Resources = context.resources

    @Provides
    fun providePreferences(context: Context): Preferences = Prefs(context = context)
}