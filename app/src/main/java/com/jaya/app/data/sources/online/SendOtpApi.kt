package com.jaya.app.data.sources.online

import com.jaya.app.core.models.responses.OtpVerificationResponse
import retrofit2.http.POST

interface SendOtpApi {

    @POST("da38f6f5b04229a89d00")
    suspend fun sendOtpVerify(): OtpVerificationResponse

}