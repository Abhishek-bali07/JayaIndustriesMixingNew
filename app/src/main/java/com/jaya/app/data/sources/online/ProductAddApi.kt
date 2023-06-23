package com.jaya.app.data.sources.online

import com.jaya.app.core.models.responses.AddProductResponse
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductAddApi {
        @GET("/0a6de7a740747434d0a9")
        suspend fun getProductInitialDetail(): AddProductResponse
}