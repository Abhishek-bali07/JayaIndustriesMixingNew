package com.jaya.app.presentation.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaya.app.mixing.R
import com.jaya.app.presentation.states.resourceString
import com.jaya.app.presentation.theme.appTextStyles
import com.jaya.app.presentation.ui.custom_composable.PinView
import com.jaya.app.presentation.ui.view_models.OtpViewModel

@Composable
fun OtpScreen(
    otpViewModel : OtpViewModel = hiltViewModel()
){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OtpScreenCoverSection()
            OtpInputSection(otpViewModel)
        }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ColumnScope.OtpInputSection(otpViewModel: OtpViewModel) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = Modifier
        .fillMaxWidth()
        .weight(1.5f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        PinView(
            pinText = otpViewModel ,
            onPinTextChange = )
    }

}

@Composable
private  fun ColumnScope.OtpScreenCoverSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.jayalogo),
            contentDescription ="",
            modifier = Modifier
                .fillMaxWidth(fraction = .8f)
                .weight(3f),
            alignment = Alignment.BottomCenter
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(fraction = .9f)
                .weight(3f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){
            Text(
                text = R.string.welcome.resourceString(),
                style = androidx.compose.material.MaterialTheme.appTextStyles.getStartedStyle
            )
            Text(
                text = R.string.verifyOtp.resourceString(),
                style = androidx.compose.material.MaterialTheme.appTextStyles.getStartedStyle,
            )
        }
    }
}
