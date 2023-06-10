package com.jaya.app.core.domain.repositories

import com.jaya.app.core.common.constants.Resource
import com.jaya.app.core.models.responses.RecentProductionResponse

interface DashboardRepository {

    suspend fun  initialProductionData(userId: String): Resource<RecentProductionResponse>
}