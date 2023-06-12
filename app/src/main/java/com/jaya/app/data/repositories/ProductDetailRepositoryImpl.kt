package com.jaya.app.data.repositories

import com.jaya.app.core.common.constants.Resource
import com.jaya.app.core.domain.repositories.ProductDetailRepository
import com.jaya.app.core.models.responses.ProductDetailResponse
import com.jaya.app.data.sources.online.ProductDetailsApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductDetailRepositoryImpl @Inject constructor(
    val productDetailsApi : ProductDetailsApi
): ProductDetailRepository {
    override suspend fun getInitialData(
        userId: String,
        productId: String
    ): Resource<ProductDetailResponse> {
      return  try {
       val result = productDetailsApi.getProductDetails();
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