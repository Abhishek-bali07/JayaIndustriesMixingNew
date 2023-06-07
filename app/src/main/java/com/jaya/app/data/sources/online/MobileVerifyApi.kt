package com.jaya.app.data.sources.online

import com.jaya.app.core.models.responses.MobileVerifyResponse
import retrofit2.http.GET
import retrofit2.http.POST

interface MobileVerifyApi {

    @GET("/c5c4317673d615f922e9")
    suspend fun verifyMobile() : MobileVerifyResponse


}