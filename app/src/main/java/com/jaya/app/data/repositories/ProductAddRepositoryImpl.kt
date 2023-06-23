package com.jaya.app.data.repositories

import android.util.Log
import com.jaya.app.core.common.constants.Resource
import com.jaya.app.core.domain.repositories.ProductAddRepository
import com.jaya.app.core.models.responses.AddProductResponse
import com.jaya.app.data.sources.online.ProductAddApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductAddRepositoryImpl @Inject constructor(
    val productAddApi : ProductAddApi
): ProductAddRepository {
    override suspend fun getInitaialData(
        userId: String): Resource<AddProductResponse> {
        return  try {
            val res = productAddApi.getProductInitialDetail()
            Resource.Success(res)
        }catch (ex: HttpException) {
            Log.d("TAGGING", "getInitialData: ${ex.message}")
            Resource.Error(ex.message())
        } catch (ex: IOException) {
            Log.d("Debugging", "getInitialData: ${ex.message}")
            Resource.Error(ex.message.toString())
        } catch (ex: Exception) {
            Log.d("Message", "getInitialData: ${ex.message}")
            Resource.Error(ex.message.toString())
        }
        }
}