package com.jaya.app.core.usecases

import android.util.Log
import com.jaya.app.core.common.constants.Data
import com.jaya.app.core.common.constants.Resource
import com.jaya.app.core.common.enums.EmitType
import com.jaya.app.core.domain.repositories.DashboardRepository
import com.jaya.app.core.utils.helper.AppStore
import kotlinx.coroutines.flow.flow

import javax.inject.Inject
import kotlin.math.log

class DashboardUseCase  @Inject constructor(
    private  val prefs : AppStore,
    private val repository: DashboardRepository
) {
    fun initialProduction() = flow<Data> {
    emit(Data(EmitType.Loading, value = true))

    when(val response  = repository.initialProductionData(prefs.userId())) {

        is Resource.Success ->{
            emit(Data(EmitType.Loading, false))
            response.data?.apply {
                when(status){
                    true ->{
                        emit(Data(type = EmitType.ProductionItem, value = this.production))
                    }
                    else -> { emit(Data(EmitType.BackendError, message))}
                }
            }
        }

        else -> {}
    }

 }
}