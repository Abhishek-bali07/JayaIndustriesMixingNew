package com.jaya.app.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jaya.app.controller.HiltControllerApp
import com.jaya.app.core.domain.repositories.SplashRepository
import com.jaya.app.core.utils.helper.AppStore
import com.jaya.app.data.repositories.SplashRepositoryImpl
import com.jaya.app.utills.helper_impl.AppStoreImpl
import com.jaya.app.utills.helper_impl.Metar
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    companion object {
        private val Context.dataStore by preferencesDataStore("JayaMixing")

        @Singleton
        @Provides
        fun provideDataStore(@ApplicationContext appContext: Context): DataStore<androidx.datastore.preferences.core.Preferences> =
            appContext.dataStore

    }

    @Binds
    fun bindSplashRepository(splashRepository: SplashRepositoryImpl): SplashRepository


    @Binds
    fun bindAppStore(appStoreImpl: AppStoreImpl): AppStore

}