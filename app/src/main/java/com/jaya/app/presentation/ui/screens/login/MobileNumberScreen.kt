package com.jaya.app.presentation.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaya.app.presentation.ui.view_models.MobileViewModel
import com.jaya.app.mixing.R



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
    }
}

@Composable
private fun ColumnScope.MobileScreenCoverSection() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .weight(2f),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Image(
            painter = painterResource(id =R.drawable.jayalogo),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth(fraction = .8f)
                .padding(vertical = 20.dp)
        )
    }
}
