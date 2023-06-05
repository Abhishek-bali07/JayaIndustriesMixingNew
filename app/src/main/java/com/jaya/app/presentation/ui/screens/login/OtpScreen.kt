package com.jaya.app.presentation.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.bsquare.app.presentation.ui.custom_composable.AppButton
import com.jaya.app.mixing.R
import com.jaya.app.presentation.states.resourceString
import com.jaya.app.presentation.theme.appTextStyles
import com.jaya.app.presentation.ui.custom_composable.PinView
import com.jaya.app.presentation.ui.view_models.MobileViewModel

@Composable
fun OtpScreen(
    mobileViewModel: MobileViewModel = hiltViewModel()
){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            OtpInputSection(mobileViewModel)
        }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ColumnScope.OtpInputSection(mobileViewModel: MobileViewModel) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = Modifier
        .fillMaxWidth()
        .weight(1.5f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = R.string.verifyOtp.resourceString(),
            style = MaterialTheme.appTextStyles.getStartedStyle,
        )

        TextField(
            value = mobileViewModel.mobileNumber.value,
            onValueChange = {
                mobileViewModel.onNumberChange(it)
            }
        )

        PinView(
            pinText = mobileViewModel.otp.value,
            onPinTextChange = {
               mobileViewModel.onOtp(it)
                if(mobileViewModel.otp.value.length == 4){
                    focusManager.clearFocus()
                    keyboardController?.hide()

                }
            }
        )


        TextButton(onClick = mobileViewModel::resendOtp) {
            Text(
                text = R.string.resendOtp.resourceString(),
                style = MaterialTheme.appTextStyles.resendCodeStyle
            )
        }
        AppButton(
            enable = mobileViewModel.venableBtn.value,
            loading = mobileViewModel.loading.value,
            action = mobileViewModel::appLogin,
            name = R.string.verify
        )
    }

}


