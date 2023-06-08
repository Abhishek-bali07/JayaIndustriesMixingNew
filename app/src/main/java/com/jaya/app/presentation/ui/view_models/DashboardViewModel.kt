package com.jaya.app.presentation.ui.view_models

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.jaya.app.core.utils.helper.AppNavigator
import com.jaya.app.utills.helper_impl.SavableMutableState
import com.jaya.app.utills.helper_impl.UiData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    savedStateHandle: SavedStateHandle,

    ):ViewModel(){

    val scaffoldState = ScaffoldState(
        drawerState = DrawerState(initialValue = DrawerValue.Closed),
        snackbarHostState = SnackbarHostState()
    )



    val drawerGuestureState = SavableMutableState(
        key = UiData.DrawerGuestureState,
        savedStateHandle = savedStateHandle,
        initialData = false
    )


}