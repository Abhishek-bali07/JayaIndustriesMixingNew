package com.jaya.app.presentation.ui.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.jaya.app.core.entities.ProductionData
import com.jaya.app.core.usecases.AddUseCase
import com.jaya.app.core.utils.helper.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddProductionViewModel @Inject constructor(
    val appNavigator: AppNavigator,
    private val useCase: AddUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

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

    fun onChangeProductName(pn : String){
        productName.value = pn
    }

    fun onChangeQuantityName(qn : String){
        quantityName.value = qn
    }




}