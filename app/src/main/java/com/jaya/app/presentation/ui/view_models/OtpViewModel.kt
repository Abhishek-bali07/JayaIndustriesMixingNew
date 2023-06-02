package com.jaya.app.presentation.ui.view_models

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.jaya.app.utills.helper_impl.SavableMutableState
import com.jaya.app.utills.helper_impl.UiData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OtpViewModel  @Inject constructor(
    savedStateHandle: SavedStateHandle,
):ViewModel(){



    val otp = SavableMutableState(
        key = UiData.DriverOtpInput,
        savedStateHandle = savedStateHandle,
        initialData = ""
    )


}