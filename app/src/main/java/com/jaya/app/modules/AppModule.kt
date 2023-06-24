package com.jaya.app.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.jaya.app.core.domain.repositories.*
import com.jaya.app.core.utils.helper.AppStore
import com.jaya.app.data.repositories.*
import com.jaya.app.utills.helper_impl.AppStoreImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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

    @Binds
    fun bindMobileRepository(MobileRepository : MobileRepositoryImpl) : MobileRepository

    @Binds
    fun bindDashboardRepository(DashDashboardRepository : DashboardRepositoryImpl): DashboardRepository


    @Binds
    fun  bindProductDetailRepository(ProductDetailRepository : ProductDetailRepositoryImpl):ProductDetailRepository


    @Binds
    fun bindProductAddRepository(ProductAddRepository: ProductAddRepositoryImpl): ProductAddRepository


}