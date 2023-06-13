package com.jaya.app.data.sources.online

import com.jaya.app.core.models.responses.ProductDetailResponse
import retrofit2.http.GET

interface ProductDetailsApi {

    @GET("/65334f71bfbdef79157f")
    suspend fun  getProductDetails(): ProductDetailResponse
}