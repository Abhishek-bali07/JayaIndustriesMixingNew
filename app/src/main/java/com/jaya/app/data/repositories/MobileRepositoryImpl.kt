package com.jaya.app.data.repositories

import com.jaya.app.core.common.constants.Resource
import com.jaya.app.core.domain.repositories.MobileRepository
import com.jaya.app.core.models.responses.MobileVerifyResponse
import com.jaya.app.core.models.responses.OtpVerificationResponse
import com.jaya.app.data.sources.online.MobileVerifyApi
import com.jaya.app.data.sources.online.SendOtpApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MobileRepositoryImpl @Inject constructor(
 val mobileVerifyApi: MobileVerifyApi,
 val sendOtpApi: SendOtpApi

) : MobileRepository{

    override suspend fun login(mobileNumber: String):
            Resource<MobileVerifyResponse> {
        return  try {
            val result = mobileVerifyApi.verifyMobile();
            Resource.Success(result)
        }catch (ex: HttpException) {
            Resource.Error(ex.message())
        } catch (ex: IOException) {
            Resource.Error(ex.message.toString())
        } catch (ex: Exception) {
            Resource.Error(ex.message.toString())
        }
    }

    override suspend fun verify(
        mobileNumber: String,
        otp: String
    ): Resource<OtpVerificationResponse> {
        return  try {
            val reslt = sendOtpApi.sendOtpVerify();
            Resource.Success(reslt)
        }catch (ex: HttpException) {
            Resource.Error(ex.message())
        } catch (ex: IOException) {
            Resource.Error(ex.message.toString())
        } catch (ex: Exception) {
            Resource.Error(ex.message.toString())
        }
    }
}