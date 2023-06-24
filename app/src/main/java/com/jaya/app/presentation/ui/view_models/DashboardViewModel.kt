package com.jaya.app.presentation.ui.view_models

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaya.app.core.common.constants.Destination
import com.jaya.app.core.common.enums.EmitType
import com.jaya.app.core.entities.Production
import com.jaya.app.core.usecases.DashboardUseCase
import com.jaya.app.core.utils.helper.AppNavigator
import com.jaya.app.presentation.states.castListToRequiredTypes
import com.jaya.app.presentation.states.castValueToRequiredTypes
import com.jaya.app.utills.helper_impl.SavableMutableState
import com.jaya.app.utills.helper_impl.UiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(
    private  val  useCase :DashboardUseCase,
    private val appNavigator: AppNavigator,
    savedStateHandle: SavedStateHandle,

    ):ViewModel(){






    val recentProduction = mutableStateListOf<Production>()


    init {
        getInitialData()
    }



    val scaffoldState = ScaffoldState(
        drawerState = DrawerState(initialValue = DrawerValue.Closed),
        snackbarHostState = SnackbarHostState()
    )



    val drawerGuestureState = SavableMutableState(
        key = UiData.DrawerGuestureState,
        savedStateHandle = savedStateHandle,
        initialData = false
    )



    fun getInitialData(){
      useCase.initialProduction().onEach {
          when(it.type){
              EmitType.Loading ->{
                  it.value?.castValueToRequiredTypes<Boolean>()?.let {

                  }

              }
              EmitType.ProductionItem ->{
                  it.value?.castListToRequiredTypes<Production>()?.let {
                      recentProduction.addAll(it)
                  }
              }

              else -> {}
          }
      }.launchIn(viewModelScope)
    }

    fun onCardClicked(production: Production){
 //       val id = productionId ?: ""
        appNavigator.tryNavigateTo(
            Destination.ProductionDetailScreen(productId = production.productId),
            popUpToRoute = Destination.DashboardScreen(),
            inclusive = false,
            isSingleTop = true
        )

    }

    fun onAddProduct(){
        appNavigator.tryNavigateTo(
            Destination.AddProductionScreen(),
            popUpToRoute = null,
            inclusive = false,
            isSingleTop = true
        )
    }


}