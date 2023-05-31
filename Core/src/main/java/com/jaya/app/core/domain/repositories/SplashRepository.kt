package com.jaya.app.core.domain.repositories

import com.jaya.app.core.common.constants.Resource
import com.jaya.app.data.responses.AppVersionResponse

interface SplashRepository {


    suspend fun  appVersion(currentVersion: Int) : Resource<com.jaya.app.data.responses.AppVersionResponse>
}