package com.jaya.app.presentation.ui.view_models

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.jaya.app.core.common.enums.EmitType
import com.jaya.app.core.usecases.MobileUseCase
import com.jaya.app.core.utils.helper.AppNavigator
import com.jaya.app.presentation.states.castValueToRequiredTypes
import com.jaya.app.utills.helper_impl.SavableMutableState
import com.jaya.app.utills.helper_impl.UiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import com.jaya.app.core.common.constants.Destination
import javax.inject.Inject

@HiltViewModel
class MobileViewModel @Inject constructor(
    private  val useCase: MobileUseCase,
    private val appNavigator: AppNavigator,
    savedStateHandle: SavedStateHandle
): ViewModel(){

   private var mobileNumber = ""

    val toastNotify = mutableStateOf("")

    val loginLoading = SavableMutableState(
        key = UiData.LoginApiLoading,
        savedStateHandle = savedStateHandle,
        initialData = false
    )


    val enableBtn = SavableMutableState(
        key = UiData.LoginContinueBtnEnable,
        savedStateHandle = savedStateHandle,
        initialData = false
    )

    fun onNumberChange(number: String){
        mobileNumber = number
        enableBtn.setValue(
            derivedStateOf {
                 mobileNumber.length == 10
            }.value)
    }



    fun appLogin(){
        useCase.sendOtp(mobileNumber = mobileNumber).onEach {
            when(it.type){
                EmitType.Loading ->{
                    it.value?.apply {
                        castValueToRequiredTypes<Boolean>()?.let {
                            loginLoading.setValue(it)
                        }
                    }
                }
                EmitType.Navigate ->{
                    it.value?.apply {
                        castValueToRequiredTypes<Destination.OtpScreen>()?.let { destination ->
                          appNavigator.navigateTo(destination())
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
        }

    }




}