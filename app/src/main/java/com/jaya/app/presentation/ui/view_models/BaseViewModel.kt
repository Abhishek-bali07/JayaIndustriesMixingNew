package com.jaya.app.presentation.ui.view_models

import androidx.lifecycle.ViewModel
import com.jaya.app.core.utils.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class BaseViewModel @Inject constructor(
    val appNavigator: AppNavigator,
) : ViewModel(){




}