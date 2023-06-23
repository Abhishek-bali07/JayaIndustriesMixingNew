package com.jaya.app.core.models.responses

import com.jaya.app.core.entities.ProductionData

data class AddProductResponse(
    val status:Boolean,
    val message: String,
    val details: ProductionData
)
