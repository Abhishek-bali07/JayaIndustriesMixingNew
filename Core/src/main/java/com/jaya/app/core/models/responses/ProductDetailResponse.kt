package com.jaya.app.core.models.responses

import com.google.gson.annotations.SerializedName
import com.jaya.app.core.entities.ProductionDetailData

data class ProductDetailResponse(

    val status: Boolean,
    val message: String,
    val details: ProductionDetailData
)
