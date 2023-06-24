package com.jaya.app.data.repositories

import android.util.Log
import com.jaya.app.core.common.constants.Resource
import com.jaya.app.core.domain.repositories.ProductDetailRepository
import com.jaya.app.core.entities.UploadData
import com.jaya.app.core.models.responses.ProductDetailResponse
import com.jaya.app.core.models.responses.UploadMixingResponse
import com.jaya.app.data.sources.online.ProductDetailsApi
import com.jaya.app.data.sources.online.UpgradeProductApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductDetailRepositoryImpl @Inject constructor(
    val productDetailsApi : ProductDetailsApi,
    val upgradedProductApi: UpgradeProductApi,
): ProductDetailRepository {
    override suspend fun getInitialData(
        userId: String,
        selectedProductID: String
    ): Resource<ProductDetailResponse> {
        return  try {
            val result = productDetailsApi.getProductDetails()
            Resource.Success(result)
        }catch (ex: HttpException) {
            Resource.Error(ex.message())
        } catch (ex: IOException) {
            Resource.Error(ex.message.toString())
        } catch (ex: Exception) {
            Resource.Error(ex.message.toString())
        }

}

    override suspend fun updateProductData(
        userId: String, uploadData: UploadData): Resource<UploadMixingResponse> {
        return try {
            val result = upgradedProductApi.updateData(userId)
            Resource.Success(result)
        }catch (ex: HttpException) {
            Log.d("message", "updateProductData: ${ex.message}")
            Resource.Error(ex.message())

        } catch (ex: IOException) {
            Log.d("message", "updateProductData: ${ex.message}")
            Resource.Error(ex.message.toString())
        } catch (ex: Exception) {
            Log.d("message", "updateProductData:${ex.message} ")


            Resource.Error(ex.message.toString())
        }
    }


}