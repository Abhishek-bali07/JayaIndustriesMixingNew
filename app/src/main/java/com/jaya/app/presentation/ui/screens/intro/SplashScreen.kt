package com.jaya.app.presentation.ui.screens.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaya.app.presentation.ui.view_models.SplashViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.jaya.app.mixing.R
import com.jaya.app.presentation.states.statusBarColor


@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = hiltViewModel(),

){
        Column(
            modifier = Modifier.fillMaxWidth()
                .background(color = Color(0xff4BB26D))
                .statusBarColor(color = Color(0xff4BB26D)),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally


            ) {
            SplashImageSection(R.drawable.jayalogo)
        }
}

@Composable
private  fun  ColumnScope.SplashImageSection(splash: Int){
    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center){
        Image(
            painter = painterResource(id = splash),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(fraction = 1.8f)



        )
    }
}
