package com.jaya.app.presentation.ui.view_models

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaya.app.core.common.constants.Destination
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
    private val useCase: DetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val selectedProductID =
        savedStateHandle.get<String>(Destination.ProductionDetailScreen.productId_KEY)

    init {
        selectedProductID?.let {
            initialData(it)
        }
    }

    val productDetails = mutableStateOf<ProductionDetailData?>(null)

    val isMenuExpanded = mutableStateOf(false)

    val isExpanded = mutableStateOf(false)

    val isUnitExpanded = mutableStateOf(false)

    val selectedPlant = mutableStateOf("")

    val selectedShift = mutableStateOf("")

    val selectedUnit = mutableStateOf("")


    val productName = mutableStateOf("")


    val srtTime = mutableStateOf("")

    val endTime = mutableStateOf("")


    fun onChangeProductName(pn : String){
        productName.value = pn
    }


    private var productIDArg = ""

    fun initialData(productId: String) {
        productIDArg = productId
        useCase.InitialDetails(productId).onEach {
            when (it.type) {
                EmitType.ProductDetails -> {
                    it.value?.castValueToRequiredTypes<ProductionDetailData>()?.let {data->
                        Log.d("meassage", "initialData: $productDetails")
                        data.plantName.map {plant ->
                            if (plant.isSelected) {
                                Log.d("TAG", "initialData: $selectedPlant")
                                selectedPlant.value = plant.plantName
                            }
                        }
                        data.shiftName.map {shift->
                            if (shift.isSelected) {
                                Log.d("message", "initialData: $selectedShift")
                                selectedShift.value = shift.shiftName
                            }
                        }
                        data.unit.map {unit->
                            if(unit.isSelected) {
                                Log.d("messaging", "initialData: $selectedUnit")
                                selectedUnit.value = unit.unitsName
                            }
                        }
                        onChangeProductName(data.productName)

                        srtTime.value = data.startTime

                        endTime.value = data.endTime

                        productDetails.value = data
                    }
                }

                else -> {}
            }

        }.launchIn(viewModelScope)
    }
}