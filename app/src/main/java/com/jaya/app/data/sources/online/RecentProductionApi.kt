package com.jaya.app.data.sources.online

import com.jaya.app.core.models.responses.RecentProductionResponse
import retrofit2.http.GET

interface RecentProductionApi {

    @GET("/e1424da24560360b8d18")
    suspend fun  getProduction() : RecentProductionResponse

}