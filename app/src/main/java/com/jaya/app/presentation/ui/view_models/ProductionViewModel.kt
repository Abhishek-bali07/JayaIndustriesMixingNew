package com.jaya.app.presentation.ui.view_models

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaya.app.core.common.constants.Destination
import com.jaya.app.core.common.enums.EmitType
import com.jaya.app.core.entities.AddedIngredents
import com.jaya.app.core.entities.ProductionDetailData
import com.jaya.app.core.entities.UploadData
import com.jaya.app.core.usecases.DetailUseCase
import com.jaya.app.core.utils.helper.AppNavigator
import com.jaya.app.presentation.states.castValueToRequiredTypes
import com.jaya.app.utills.helper_impl.SavableMutableState
import com.jaya.app.utills.helper_impl.UiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
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
            initialData()
            validateInputs()

        }
    }

    val productDetails = mutableStateOf<ProductionDetailData?>(null)

    val isMenuExpanded = mutableStateOf(false)

    val isExpanded = mutableStateOf(false)

    val isUnitExpanded = mutableStateOf(false)

    val isIngredentsUnitExpanded = mutableStateOf(false)

    val selectedPlant = mutableStateOf("")

    val selectedShift = mutableStateOf("")

    val selectedUnit = mutableStateOf("")


    val productName = mutableStateOf("")

    val quantityName = mutableStateOf("")



    val toastNotify = mutableStateOf("")



    val srtTime = mutableStateOf("")

    val endTime = mutableStateOf("")



    val ingredentName = mutableStateOf("")
    val ingredentQtty = mutableStateOf("")
    val ingredentUnit = mutableStateOf("")


    val addIngredents = mutableStateListOf<AddedIngredents?>(null)


    fun onChangeIngredentName(ing :String){
        ingredentName.value = ing
    }

    fun onChangeIngredentQtty(qty :String){
        ingredentQtty.value = qty
    }

    fun onChangeProductName(pn : String){
        productName.value = pn
    }

    fun onChangeQuantityName(qn : String){
        quantityName.value = qn
    }

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


    private var productIDArg = ""

    fun initialData() {
//        productIDArg = productId
        if (selectedProductID != null) {
            useCase.InitialDetails(selectedProductID).onEach {
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
                                    selectedUnit.value = unit.unitName
                                }
                            }
                            onChangeProductName(data.productName)

                            onChangeQuantityName(data.productQty)

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
        if (selectedProductID != null) {
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



    private fun validateInputs() {
        viewModelScope.launch {
            while (true) {
                delay(200L)
                enableBtn.setValue(
                    when{
                        selectedPlant.value.isEmpty() ->{false}
                        selectedShift.value.isEmpty() ->{false}
                        selectedUnit.value.isEmpty() ->{false}
                        productName.value.isEmpty() ->{false}
                        quantityName.value.isEmpty() ->{false }
                        srtTime.value.isEmpty() ->{false}
                        endTime.value.isEmpty() ->{false}
                        ingredentName.value.isEmpty() ->{false}
                        ingredentQtty.value.isEmpty() ->{false}
                        ingredentUnit.value.isEmpty() ->{false}
                        else -> true
                    }
                )
            }
        }
    }

}