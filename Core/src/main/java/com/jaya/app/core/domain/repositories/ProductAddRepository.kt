package com.jaya.app.core.domain.repositories

import com.jaya.app.core.common.constants.Resource
import com.jaya.app.core.models.responses.AddProductResponse

interface ProductAddRepository {
    suspend fun  getInitaialData(userId: String):Resource<AddProductResponse>
}