package com.jaya.app.data.repositories

import com.jaya.app.core.common.constants.Resource
import com.jaya.app.core.domain.repositories.SplashRepository
import com.jaya.app.core.entities.AppVersion
import com.jaya.app.core.entities.responses.AppVersionResponse
import com.jaya.app.data.sources.online.AppVersionApi
import kotlinx.coroutines.delay
import retrofit2.Retrofit
import javax.inject.Inject

class SplashRepositoryImpl @Inject constructor(
    val appVersionApi: AppVersionApi
    ) : SplashRepository  {
    override suspend fun appVersion(currentVersion: Int): Resource<AppVersionResponse> {
        return appVersionApi.getVersion()
    }
}