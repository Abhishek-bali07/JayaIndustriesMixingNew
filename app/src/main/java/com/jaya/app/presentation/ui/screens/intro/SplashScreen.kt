package com.jaya.app.presentation.ui.screens.intro

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaya.app.presentation.ui.view_models.SplashViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jaya.app.core.common.enums.IntroStatus
import com.jaya.app.core.entities.AppVersion
import com.jaya.app.mixing.R
import com.jaya.app.presentation.states.*
import com.jaya.app.utills.helper_impl.SavableMutableState


@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = hiltViewModel(),

){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xff4BB26D))
                .statusBarColor(color = Color(0xff4BB26D)),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally


            ) {
            SplashImageSection(R.drawable.jayalogo)


            SplashDescriptionSection(
                onClick = {
                     splashViewModel.onSplashBtnClicked()
                },
                isBtnVisible = splashViewModel.splashBtnStatus,
            )


        }


    splashViewModel.versionUpdateDialog.value?.apply {
        if(currentState()){
            AlertDialog(
                shape = RoundedCornerShape(19.dp),
                onDismissRequest = {
                    if((currentData?.data as AppVersion).isSkipable){
                        setState(Dialog.Companion.State.DISABLE)
                    }
                },
                buttons = {
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if ((currentData?.data as AppVersion).isSkipable) {
                            currentData?.negative?.let {
                                OutlinedButton(
                                    onClick = { onConfirm?.invoke(null) },
                                    shape = RoundedCornerShape(30),
                                    border = BorderStroke(1.dp, color = Color(0xff4BB26D)),
                                    modifier = Modifier.padding(10.dp)
                                ) {
                                    Text(text = it,
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            color = Color(0xff4BB26D)
                                        ),
                                    )
                                }
                            }
                        }
                        currentData?.positive?.let {
                            OutlinedButton(
                                onClick = { onConfirm?.invoke(currentData?.data) },
                                shape = RoundedCornerShape(30),
                                border = BorderStroke(1.dp, color =Color(0xff4BB26D)),
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Text(text = it,
                                    style =TextStyle(
                                        fontSize = 12.sp,
                                        color = Color(0xff4BB26D)
                                    ),
                                )
                            }
                        }
                    }
                },
                modifier = Modifier,
                title = {
                    currentData?.title?.let {
                        Text(
                            text = it,
                            style = TextStyle(
                                fontSize = 20.sp,
                                color = Color.Black,

                                ),
                        )
                    }

                },
                text = {
                    currentData?.message?.let {
                        Text(text = it,
                            style = TextStyle(
                                fontSize = 17.sp,
                                color = Color.Black,
                            ),)
                    }
                }

            )
        }
    }
    EffectHandlers(splashViewModel)
}

@Composable
fun EffectHandlers(splashViewModel: SplashViewModel) {
    val localContext = LocalContext.current

    splashViewModel.versionUpdateLink.OnEffect(
        intentionalCode = { link->
            link?.let { lk->
                if(lk.isNotEmpty()) {
                    try {
                        val appStoreIntent = Intent(Intent.ACTION_VIEW).also {
                            it.data = Uri.parse(lk)
                        }
                        localContext.startActivity(appStoreIntent)
                    } catch (exception: Exception) {
                        R.string.unable_to_open_play_store.string().shortToast(localContext)
                    }
                }
            }
        },
        clearance = {""}
    )

}


@Composable
private fun ColumnScope.SplashDescriptionSection(
    onClick: () -> Unit,
    isBtnVisible: SavableMutableState<IntroStatus>
) {

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        if (isBtnVisible.value == IntroStatus.NOT_DONE){
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
