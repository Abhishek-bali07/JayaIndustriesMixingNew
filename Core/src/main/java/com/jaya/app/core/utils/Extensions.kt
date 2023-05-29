package com.jaya.app.core.utils


import com.jaya.app.core.common.constants.Data
import com.jaya.app.core.common.constants.Resource
import com.jaya.app.core.common.enums.EmitType
import kotlinx.coroutines.flow.FlowCollector


suspend inline fun <reified R> FlowCollector<Data>.handleFailedResponse(
    response: Resource<R>,
    message: String?,
    emitType: EmitType
) {
    when (message != null) {
        true -> {
            emit(Data(emitType, message))
        }
        else -> {
            emit(Data(emitType, response.message))
        }
    }
}