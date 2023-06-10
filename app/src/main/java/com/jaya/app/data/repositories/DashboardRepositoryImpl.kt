package com.jaya.app.data.repositories

import com.jaya.app.core.common.constants.Resource
import com.jaya.app.core.domain.repositories.DashboardRepository
import com.jaya.app.core.models.responses.RecentProductionResponse
import com.jaya.app.data.sources.online.RecentProductionApi
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    val recentProductionApi: RecentProductionApi

):DashboardRepository{


    override suspend fun initialProductionData(
        userId: String
    ): Resource<RecentProductionResponse> {
        return try {
           val result = recentProductionApi.getProduction();
            Resource.Success(result)
       }catch (ex: HttpException) {
            Resource.Error(ex.message())
        } catch (ex: IOException) {
            Resource.Error(ex.message.toString())
        } catch (ex: Exception) {
            Resource.Error(ex.message.toString())
        }
    }
}