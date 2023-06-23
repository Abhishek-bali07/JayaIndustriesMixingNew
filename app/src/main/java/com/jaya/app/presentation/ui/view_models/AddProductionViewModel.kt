package com.jaya.app.presentation.ui.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaya.app.core.common.constants.Data
import com.jaya.app.core.common.enums.EmitType
import com.jaya.app.core.entities.ProductionData
import com.jaya.app.core.usecases.AddUseCase
import com.jaya.app.core.utils.helper.AppNavigator
import com.jaya.app.presentation.states.castValueToRequiredTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddProductionViewModel @Inject constructor(
    val appNavigator: AppNavigator,
    private val useCase: AddUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {


    init {
        initialDetail()
    }

    val productDetails = mutableStateOf<ProductionData?>(null)


    val selectedShift = mutableStateOf("")

    val selectedPlant = mutableStateOf("")


    val productName = mutableStateOf("")

    val srtTime = mutableStateOf("")

    val endTime = mutableStateOf("")

    val quantityName = mutableStateOf("")


    val selectedUnit = mutableStateOf("")

    val isExpanded = mutableStateOf(false)
    val isMenuExpanded = mutableStateOf(false)
    val isIngredentsUnitExpanded =  mutableStateOf(false)


    val ingredentName = mutableStateOf("")
    val ingredentQtty = mutableStateOf("")
    val ingredentUnit = mutableStateOf("")

    fun onChangeProductName(pn : String){
        productName.value = pn
    }

    fun onChangeQuantityName(qn : String){
        quantityName.value = qn
    }


    fun onChangeIngredntName(ing :String){
        ingredentName.value = ing
    }

    fun onChangeIngredntQtty(qty :String){
        ingredentQtty.value = qty
    }

    private var productIDArg = ""

    fun initialDetail(){
        useCase.InitialDetails() .onEach {
            when(it.type){
                EmitType.ProductDetails ->{
                   it.value?.castValueToRequiredTypes<ProductionData>()?.let { data ->
                   data.plantName.map { plant ->
                       if(plant.isSelected){
                           selectedPlant.value = plant.plantName
                       }
                   }
                   data.shiftName.map {shift ->
                       if (shift.isSelected){
                           selectedShift.value = shift.shiftName
                       }
                   }
                   data.unit.map {  unit->
                       if(unit.isSelected){
                           selectedUnit.value = unit.unitName
                       }
                   }
                       productDetails.value =data


                   }
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }




}