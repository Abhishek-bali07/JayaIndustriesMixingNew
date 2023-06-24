package com.jaya.app.presentation.ui.view_models

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaya.app.core.common.constants.Destination
import com.jaya.app.core.common.enums.EmitType
import com.jaya.app.core.entities.AddedIngredents
import com.jaya.app.core.entities.ProductionData
import com.jaya.app.core.entities.UploadData
import com.jaya.app.core.usecases.AddUseCase
import com.jaya.app.core.utils.helper.AppNavigator
import com.jaya.app.presentation.states.castValueToRequiredTypes
import com.jaya.app.utills.helper_impl.SavableMutableState
import com.jaya.app.utills.helper_impl.UiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    val addIngredents = mutableStateListOf<AddedIngredents?>(null)

    val ingredentName = mutableStateOf("")
    val ingredentQtty = mutableStateOf("")
    val ingredentUnit = mutableStateOf("")



    val mixingLoading = SavableMutableState(
        key = UiData.SaveIngredientLoading,
        savedStateHandle =savedStateHandle,
        initialData = false
    )

    val enableBtn = SavableMutableState(
        key = UiData.SaveBtnEnable,
        savedStateHandle = savedStateHandle,
        initialData = false
    )


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

    fun addIngredient() {
        val addedIngredient = AddedIngredents(
            ingName = ingredentName.value,
            ingQtty = ingredentQtty.value,
            selectedUnit = ingredentUnit.value

        )
        addIngredents.add(addedIngredient)
    }



    fun removeIngredient(item: AddedIngredents) {
        addIngredents.remove(item)
    }



    fun uploadMixingData(){
        val uploadData = UploadData(
            productName = productName.value,
            selectedPlant = selectedPlant.value,
            selectedShift = selectedShift.value,
            startTime = srtTime.value,
            endTime = endTime.value,
            productQty = quantityName.value,
            selectedUnit = selectedUnit.value,
            upgradedIngredents = addIngredents.toList()
        )
        useCase.addProductData(uploadData,selectedProductID).onEach {
            when(it.type){
                EmitType.Loading ->{
                    it.value?.apply {
                        castValueToRequiredTypes<Boolean>()?.let {
                            mixingLoading.setValue(it)
                        }
                    }
                }
                EmitType.Inform ->{
                    it.value?.apply {
                        castValueToRequiredTypes<Boolean>()?.let {

                        }
                    }
                }

                EmitType.Navigate -> {
                    it.value?.apply {
                        castValueToRequiredTypes<Destination>()?.let { destination ->
                            appNavigator.tryNavigateBack()
                        }
                    }
                }
                EmitType.NetworkError -> {
                    it.value?.apply {
                        castValueToRequiredTypes<String>()?.let {
                            toastNotify.value = it
                        }
                    }
                }
                EmitType.BackendError -> {
                    it.value?.apply {
                        castValueToRequiredTypes<String>()?.let {
                            toastNotify.value = it
                        }
                    }
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }


}