package com.jaya.app.presentation.ui.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaya.app.core.common.constants.Destination
import com.jaya.app.core.common.constants.DialogData
import com.jaya.app.core.common.enums.EmitType
import com.jaya.app.core.common.enums.IntroStatus
import com.jaya.app.core.entities.AppVersion
import com.jaya.app.core.usecases.SplashUseCase
import com.jaya.app.core.utils.helper.AppNavigator
import com.jaya.app.mixing.R
import com.jaya.app.presentation.states.Dialog
import com.jaya.app.presentation.states.castValueToRequiredTypes
import com.jaya.app.utills.helper_impl.SavableMutableState
import com.jaya.app.utills.helper_impl.UiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private  val appNavigator: AppNavigator,
    private val splashUseCases: SplashUseCase,
    savedStateHandle: SavedStateHandle

) :ViewModel() {


   init {
       viewModelScope.launch {
           checkAppVersion()
           checkIntroStatus()

       }


   }

    val splashBtnStatus = SavableMutableState(
        key = UiData.IntroStatusKey,
        savedStateHandle = savedStateHandle,
        initialData = IntroStatus.NOT_DONE
    )

    val versionUpdateDialog = mutableStateOf<Dialog?>(null)


    private suspend fun checkIntroStatus() {
        splashUseCases.checkIntroStatus()
            .flowOn(Dispatchers.Default).collect { dataEntry ->
                when (dataEntry.type) {
                    EmitType.IntroStatus -> {
                        dataEntry.value?.apply {
                            castValueToRequiredTypes<IntroStatus>()?.let {
                                splashBtnStatus.setValue(it)
                            }
                        }
                    }

                    else -> {}
                }
            }
    }

    fun onSplashBtClicked(destination: Destination.NoArgumentsDestination = Destination.MobileNumberScreen) {
        appNavigator.tryNavigateTo(
            destination(),
            popUpToRoute = Destination.SplashScreen(),
            isSingleTop = true,
            inclusive = true
        )
    }


    private suspend fun checkAppVersion(){
        splashUseCases.checkAppVersion().flowOn(Dispatchers.IO).collect{
            when(it.type){
                EmitType.BackendError ->{
                    it.value?.apply {
                        castValueToRequiredTypes<String>()?.let {

                        }
                    }
                }

                EmitType.AppVersion ->{
                    it.value?.apply {
                        castValueToRequiredTypes<AppVersion>()?.let { appVersion ->
                            versionUpdateDialog.value = Dialog(
                                data = DialogData(
                                    title = R.string.version_update.toString(),
                                    message = appVersion.versionMessage,
                                    positive = R.string.update.toString(),
                                    negative = R.string.later.toString(),
                                    data = appVersion
                                )
                            )
                            handelDialogEvents()
                        }
                    }
                }
                EmitType.Navigate ->{
                    it.value?.castValueToRequiredTypes<Destination.NoArgumentsDestination>()?.let {
                        onSplashBtClicked(it)
                    }
                }

                EmitType.NetworkError->{
                    it.value.apply {
                        castValueToRequiredTypes<String>()?.let {

                        }
                    }
                }



                else -> {}
            }
        }
    }

    /*val versionUpdateLink = SavableMutableState<String?>(
        key = UiData.AppStoreLink,
        savedStateHandle = savedStateHandle,
        initialData = null
    )*/


    private fun handelDialogEvents() {
       versionUpdateDialog.value?.onConfirm = {
           it?.castValueToRequiredTypes<AppVersion>()?.apply {
              //  versionUpdateLink.setValue(link)
           }
       }
        versionUpdateDialog.value?.onDismiss = {
            versionUpdateDialog.value?.setState(Dialog.Companion.State.DISABLE)
            splashUseCases.navigateToAppropiateScreen().onEach {
                when(it.type){
                    EmitType.Navigate ->{
                             it.value.apply {
                                 castValueToRequiredTypes<Destination.NoArgumentsDestination>()?.let {
                                     appNavigator.navigateTo(
                                         it(),
                                         popUpToRoute = Destination.SplashScreen(),
                                          inclusive = true
                                     )
                                 }
                             }
                        }

                    else -> {}
                }
            }.launchIn(viewModelScope)
        }
    }


}