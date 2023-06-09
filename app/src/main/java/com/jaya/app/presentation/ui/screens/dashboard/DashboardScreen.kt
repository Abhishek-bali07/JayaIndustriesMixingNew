package com.jaya.app.presentation.ui.screens.dashboard

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.Scaffold
import com.jaya.app.mixing.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaya.app.presentation.states.resourceImage
import com.jaya.app.presentation.states.resourceString
import com.jaya.app.presentation.theme.appTextStyles
import com.jaya.app.presentation.ui.view_models.DashboardViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@Composable
 fun DashBoardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
 ) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        drawerContent = {SideBarContent(viewModel = viewModel)},
        drawerScrimColor = Color.Black.copy(alpha = .5f),
        drawerBackgroundColor = Color.White,
        scaffoldState = viewModel.scaffoldState,
        drawerGesturesEnabled = viewModel.drawerGuestureState.value,
        topBar = { AppBarContent(viewModel) },

    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Cyan)
                .padding(paddingValues),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {}
        }
    }
 }

@Composable
fun AppBarContent(viewModel: DashboardViewModel) {
    val uiScope = rememberCoroutineScope()

    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Color(0xffFFEB56),
        contentPadding = PaddingValues(8.dp),
    ) {
        IconButton(onClick = {
            uiScope.launch {
            }

        }) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.size(35.dp)
            )
        }



        DrawerImageSection(R.drawable.jayalogo)



        IconButton(onClick = {
            uiScope.launch {
            }

        }) {
            Icon(
                painter = R.drawable.bell.resourceImage(),
                contentDescription ="" ,
                modifier = Modifier.size(35.dp)
                )

        }

    }
}



@Composable
fun DrawerImageSection(@DrawableRes jayalogo: Int) {

    Image(
        painter = painterResource(id = jayalogo),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(fraction = 0.8f)
    )

}

@Composable
 private fun SideBarContent(viewModel: DashboardViewModel) {

    val uiScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top
    ) {
        AppLogoSection()
        DrawerMenuLogoutItem(viewModel, uiScope)
    }

}

@Composable
private fun ColumnScope.AppLogoSection() {

}

@Composable
 private  fun DrawerMenuLogoutItem(viewModel: DashboardViewModel, uiScope: CoroutineScope) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 25.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.jayalogo),
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .padding(end = 15.dp)
        )

        Text(
            text = R.string.logout.resourceString(),
            style = MaterialTheme.appTextStyles.drawerMenuItemStyle
        )
    }
}
