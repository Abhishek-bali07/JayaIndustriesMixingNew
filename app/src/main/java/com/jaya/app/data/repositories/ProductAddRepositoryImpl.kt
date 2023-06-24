package com.jaya.app.data.repositories

import com.jaya.app.core.common.constants.Resource
import com.jaya.app.core.domain.repositories.ProductAddRepository
import com.jaya.app.core.models.responses.AddProductResponse
import javax.inject.Inject

class ProductAddRepositoryImpl @Inject constructor(

):ProductAddRepository {
    override suspend fun getInitaialData(userId: String): Resource<AddProductResponse> {
        TODO("Not yet implemented")
    }
}