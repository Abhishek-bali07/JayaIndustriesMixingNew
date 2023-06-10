package com.jaya.app.core.models.responses

import com.jaya.app.core.entities.Production

data class RecentProductionResponse(
    val status: Boolean,
    val message: String,
    val production: List<Production>

)
