package com.jaya.app.core.usecases

import com.jaya.app.core.common.constants.Data
import com.jaya.app.core.common.constants.Resource
import com.jaya.app.core.common.enums.EmitType
import com.jaya.app.core.domain.repositories.ProductAddRepository
import com.jaya.app.core.utils.helper.AppStore
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddUseCase  @Inject constructor(
    private val prfs: AppStore,
    private val  repository: ProductAddRepository
){
    fun InitialDetails() = flow<Data> {
        emit(Data(EmitType.Loading, value= true))
        when(val response = repository.getInitaialData(prfs.userId()))  {
            is Resource.Success ->{
                emit(Data(EmitType.Loading,false))
                response.data?.apply {

                    when(status){
                            true ->{
                                emit(Data(EmitType.ProductInfo, value = details))
                            }
                        else -> {
                            emit(Data(EmitType.BackendError, message))
                        }
                    }
                }
            }
            else -> {}
        }
    }


}