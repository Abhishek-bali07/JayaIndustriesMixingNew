package com.jaya.app.modules

import com.jaya.app.core.utils.helper.AppNavigator
import com.jaya.app.navigation.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Singleton
    @Binds
    fun bindNavigator(appNavigatorImpl: AppNavigatorImpl): AppNavigator
}