package com.jaya.app.data.sources.online

import com.jaya.app.core.models.responses.ProductDetailResponse
import retrofit2.http.GET

interface ProductDetailsApi {

    @GET("/cdfd6c14dd5381ac3fc5")
    suspend fun  getProductDetails(): ProductDetailResponse
}