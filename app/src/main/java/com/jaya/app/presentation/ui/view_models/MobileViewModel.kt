package com.jaya.app.presentation.ui.view_models

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.jaya.app.core.utils.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MobileViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    savedStateHandle: SavedStateHandle
): ViewModel(){
}