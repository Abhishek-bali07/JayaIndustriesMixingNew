package com.jaya.app.presentation.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaya.app.presentation.ui.view_models.MobileViewModel
import com.jaya.app.mixing.R
import com.jaya.app.presentation.states.resourceString
import com.jaya.app.presentation.theme.appTextStyles


@Composable
fun  MobileNumberScreen(
    mobileViewModel: MobileViewModel = hiltViewModel()
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        MobileScreenCoverSection()
        MobileNumberInputSection()
    }
}

@Composable
private fun ColumnScope.MobileNumberInputSection() {

}

@Composable
private fun ColumnScope.MobileScreenCoverSection() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .weight(2f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Image(
            painter = painterResource(id =R.drawable.jayalogo),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth(fraction = .8f)
                .padding(vertical = 20.dp)
        )
        Text(
            text = R.string.welcome.resourceString(),
            style = MaterialTheme.appTextStyles.getStartedStyle
        )
        Text(
            text = R.string.loginhere.resourceString(),
            style = MaterialTheme.appTextStyles.dialogMessageStyle
        )
        Column( modifier = Modifier.fillMaxWidth(fraction = .9f),
            horizontalAlignment = Alignment.Start) {


        }
    }
}
