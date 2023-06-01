package com.jaya.app.core.usecases

import com.jaya.app.core.common.constants.Data
import com.jaya.app.core.common.constants.Destination
import com.jaya.app.core.common.constants.Resource
import com.jaya.app.core.common.enums.EmitType
import com.jaya.app.core.domain.repositories.MobileRepository
import com.jaya.app.core.utils.handleFailedResponse
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MobileUseCase @Inject constructor(
    private val repository: MobileRepository,

) {

    fun sendOtp(mobileNumber: String) = flow {
        emit(Data(EmitType.Loading, true))
        when(val response = repository.login(mobileNumber)){

            is Resource.Success ->{
                emit(Data(EmitType.Loading, false))
                response.data?.apply {
                    when(status){
                        true ->{
                            emit(Data(type = EmitType.Inform, message))
                            emit(Data(type = EmitType.Navigate, Destination.OtpScreen))
                        }

                        else -> {
                            emit(Data(EmitType.BackendError, message))
                        }
                    }
                }
            }
            is Resource.Error ->{
                emit(Data(EmitType.Loading, false))
                handleFailedResponse(
                    response = response,
                    message = response.message,
                    emitType = EmitType.NetworkError
                )
            }

            else -> {}
        }
    }
}