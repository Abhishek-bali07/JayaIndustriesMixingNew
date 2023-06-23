package com.jaya.app.core.domain.repositories

import com.jaya.app.core.common.constants.Resource
import com.jaya.app.core.models.responses.AddIngredentResponse
import com.jaya.app.core.models.responses.ProductDetailResponse

interface ProductDetailRepository {

    suspend fun  getInitialData(userId: String, productId: String):Resource<ProductDetailResponse>



    suspend fun addIngredientData(userId: String, productId: String):Resource<AddIngredentResponse>



}