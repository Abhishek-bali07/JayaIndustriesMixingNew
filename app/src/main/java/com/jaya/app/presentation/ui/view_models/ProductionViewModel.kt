package com.jaya.app.presentation.ui.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaya.app.core.common.enums.EmitType
import com.jaya.app.core.entities.ProductionDetailData
import com.jaya.app.core.usecases.DetailUseCase
import com.jaya.app.core.utils.helper.AppNavigator
import com.jaya.app.presentation.states.castValueToRequiredTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class ProductionViewModel @Inject constructor(
    val appNavigator: AppNavigator,
    private  val useCase: DetailUseCase
) : ViewModel(){

    val productDetails = mutableStateOf<ProductionDetailData?>(null)

    val isMenuExpanded = mutableStateOf(false)

    val isExpanded = mutableStateOf(false)

    val mSelectedText = mutableStateOf("")

    val lSelectedText = mutableStateOf("")


    private var productIDArg = ""

    fun initialData(productId: String){
        productIDArg = productId
        useCase.InitialDetails(productId).onEach {
            when(it.type){
              EmitType.ProductDetails -> {
                    it.value?.castValueToRequiredTypes<ProductionDetailData>()?.let {
                      productDetails.value = it
                    }
              }

                else -> {}
            }

        }.launchIn(viewModelScope)
    }
}