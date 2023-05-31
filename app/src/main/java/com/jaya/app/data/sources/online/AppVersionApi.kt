package com.jaya.app.data.sources.online

import com.jaya.app.core.models.responses.AppVersionResponse
import retrofit2.http.GET


interface AppVersionApi {


    @GET("/256371f25b5a2d3e804b")
    suspend fun getVersion(): AppVersionResponse

}