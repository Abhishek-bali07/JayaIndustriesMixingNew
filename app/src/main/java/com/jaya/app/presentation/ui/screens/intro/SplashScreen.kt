package com.jaya.app.presentation.ui.screens.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaya.app.core.common.enums.IntroStatus
import com.jaya.app.mixing.R
import com.jaya.app.presentation.states.statusBarColor
import com.jaya.app.presentation.ui.view_models.SplashViewModel
import com.jaya.app.utills.helper_impl.SavableMutableState


@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = hiltViewModel(),

){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xff4BB26D))
                .statusBarColor(color = Color(0xff4BB26D)),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally


            ) {
            SplashImageSection(R.drawable.jayalogo)
            SplashDescriptionSection(
                onClick = splashViewModel::onSplashBtClicked,
                isBtnVisible = splashViewModel.splashBtnStatus,
            )


        }



}




@Composable
private fun ColumnScope.SplashDescriptionSection(
    onClick: () -> Unit,
    isBtnVisible: SavableMutableState<IntroStatus>
) {

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .padding(vertical = 30.dp)
                .size(60.dp)
                .clip(CircleShape)
                .background(color = Color(0xff4BB26D)),
        ){
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.padding(8.dp),
            )
        }
    }

}

@Composable
private  fun  ColumnScope.SplashImageSection(splash: Int){
    Box(
        modifier = Modifier.weight(1f),
        contentAlignment = Alignment.Center){
        Image(
            painter = painterResource(id = splash),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(fraction = .8f)



        )
    }
}
