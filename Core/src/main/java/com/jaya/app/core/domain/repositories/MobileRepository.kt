package com.jaya.app.core.domain.repositories

import com.jaya.app.core.common.constants.Resource
import com.jaya.app.core.models.responses.MobileVerifyResponse
import com.jaya.app.core.models.responses.OtpVerificationResponse


interface MobileRepository {
    suspend fun login(mobileNumber: String): Resource<MobileVerifyResponse>


    suspend fun verify(mobileNumber: String, otp: String): Resource<OtpVerificationResponse>
}