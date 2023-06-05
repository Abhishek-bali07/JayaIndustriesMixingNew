package com.jaya.app.data.repositories

import com.jaya.app.core.common.constants.Resource
import com.jaya.app.core.domain.repositories.MobileRepository
import com.jaya.app.core.models.responses.MobileVerifyResponse
import com.jaya.app.core.models.responses.OtpVerificationResponse
import javax.inject.Inject

class MobileRepositoryImpl @Inject constructor(
) : MobileRepository{

    override suspend fun login(mobileNumber: String): Resource<MobileVerifyResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun verify(
        mobileNumber: String,
        otp: String
    ): Resource<OtpVerificationResponse> {
        TODO("Not yet implemented")
    }
}