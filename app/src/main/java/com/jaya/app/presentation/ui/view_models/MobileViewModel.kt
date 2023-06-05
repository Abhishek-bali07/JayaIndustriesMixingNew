package com.jaya.app.presentation.ui.view_models

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaya.app.core.common.constants.AppRoutes.OTP
import com.jaya.app.core.common.enums.EmitType
import com.jaya.app.core.usecases.MobileUseCase
import com.jaya.app.core.utils.helper.AppNavigator
import com.jaya.app.presentation.states.castValueToRequiredTypes
import com.jaya.app.utills.helper_impl.SavableMutableState
import com.jaya.app.utills.helper_impl.UiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import com.jaya.app.core.common.constants.Destination
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class MobileViewModel @Inject constructor(
    private  val useCase: MobileUseCase,
    private val appNavigator: AppNavigator,
    savedStateHandle: SavedStateHandle
): ViewModel(){

   val mobileNumber = mutableStateOf("")


    val loading = mutableStateOf(false)

    val toastInform = mutableStateOf("")

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
        mobileNumber.value = number
        enableBtn.setValue(
            derivedStateOf {
                 mobileNumber.value.length == 10
            }.value)
    }




    val venableBtn = mutableStateOf(false)

    fun onOtp(input: String){
        otp.setValue(input)
        venableBtn.value = otp.value.length == 4
    }



    val otp = SavableMutableState(
        key = UiData.DriverOtpInput,
        savedStateHandle = savedStateHandle,
        initialData = ""
    )



    fun otpSend(){
        useCase.sendOtp(mobileNumber = mobileNumber.value).onEach {
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

    fun resendOtp(){
        useCase.sendOtp(mobileNumber.value).onEach {

            when(it.type){
                    EmitType.Inform ->{
                        it.value?.apply {
                            castValueToRequiredTypes<String>()?.let {
                                toastInform.value = it
                            }
                        }
                    }
                    EmitType.BackendError ->{
                        it.value?.apply {
                            castValueToRequiredTypes<String>()?.let {
                                toastInform.value = it
                            }
                        }
                    }
                EmitType.NetworkError -> {
                    it.value?.apply {
                        castValueToRequiredTypes<String>()?.let {
                            toastInform.value = it
                        }
                    }
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }


    fun appLogin(){
        useCase.verify(
            mobileNumber = mobileNumber.value,
            otp = otp.value
        ).onEach {
            when(it.type){
                EmitType.Loading -> {
                    it.value?.apply {
                        castValueToRequiredTypes<Boolean>()?.let {
                            loading.value = it
                        }
                    }
                }
                EmitType.Navigate -> {
                    it.value?.apply {
                        castValueToRequiredTypes<Destination.NoArgumentsDestination>()?.let {
                            appNavigator.navigateTo(
                                it(),
                                popUpToRoute = Destination.MobileNumberScreen(),
                                inclusive = true,
                                isSingleTop = true
                            )
                        }
                    }
                }
                EmitType.NetworkError -> {
                    it.value?.apply {
                        castValueToRequiredTypes<String>()?.let {
                            toastInform.value = it
                        }
                    }
                }
                EmitType.BackendError -> {
                    it.value?.apply {
                        castValueToRequiredTypes<String>()?.let {
                            toastInform.value = it
                        }
                    }
                }
                else -> {}
            }
        }.launchIn(viewModelScope)

    }





}