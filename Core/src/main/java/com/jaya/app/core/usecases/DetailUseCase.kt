package com.jaya.app.core.usecases

import com.jaya.app.core.common.constants.Data
import com.jaya.app.core.common.constants.Resource
import com.jaya.app.core.common.enums.EmitType
import com.jaya.app.core.domain.repositories.ProductDetailRepository
import com.jaya.app.core.utils.helper.AppStore
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailUseCase @Inject constructor(
     private val prefs : AppStore,
     private val repository: ProductDetailRepository
) {
     fun InitialDetails(productId:String) = flow<Data> {
          emit(Data(EmitType.Loading, value = true))
          when(val response = repository.getInitialData(productId, prefs.userId())){
               is Resource.Success ->{
                    emit(Data(EmitType.Loading, false))
                    response.data?.apply {
                         when(status){
                               true ->{
                                    emit(Data(EmitType.ProductDetails, value = details))
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