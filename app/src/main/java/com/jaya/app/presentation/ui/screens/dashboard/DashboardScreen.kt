package com.jaya.app.presentation.ui.screens.dashboard

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaya.app.core.entities.Production
import com.jaya.app.mixing.R
import com.jaya.app.presentation.states.resourceImage
import com.jaya.app.presentation.states.resourceString
import com.jaya.app.presentation.theme.appTextStyles
import com.jaya.app.presentation.ui.view_models.DashboardViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
 fun DashBoardScreen(

    viewModel: DashboardViewModel = hiltViewModel(),

 ) {

    val listState = rememberLazyListState()
    val fabVisibility by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex == 0
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        drawerContent = {SideBarContent(viewModel = viewModel)},
        drawerScrimColor = Color.Black.copy(alpha = .5f),
        drawerBackgroundColor = Color.White,
        scaffoldState = viewModel.scaffoldState,
        drawerGesturesEnabled = viewModel.drawerGuestureState.value,
        topBar = { AppBarContent(viewModel) },
        floatingActionButton = {
            AnimatedVisibility(visible = fabVisibility) {
                if (fabVisibility){
                    FloatingActionButton(
                        modifier = Modifier.padding(top = 80.dp),
                        onClick = {
                             viewModel.onAddBtnClicked()
                        },
                        backgroundColor = Color(0xffFFEB56),
                        contentColor = Color.Black
                    ) {
                        Icon(Icons.Filled.Add, "")
                    }
                }

            }

        }

    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.TopStart
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(5.dp))
                StatusSection(viewModel)

                Spacer(modifier = Modifier.height(10.dp))
                Divider(color = Color.LightGray, modifier = Modifier
                    .fillMaxWidth()
                    .width(1.dp))

                RecentTextSection(viewModel)

                RecentProductionList(production =viewModel.recentProduction , viewModel, listState)

            }
        }
    }
 }

@Composable
fun RecentProductionList(
    production: List<Production>,
    viewModel: DashboardViewModel,
    listState: LazyListState,
    ) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = listState,
    ){
        Log.d("testing", "RecentProductionList: ${production.toString()}")
        items(production){item ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.onCardClicked(item) }
                    .padding(10.dp)
                    .height(150.dp), elevation = 5.dp,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Row(
                        modifier = Modifier
                            .weight(2f)
                            .background(color = Color.White),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(3f)
                                .padding(horizontal = 10.dp, vertical = 10.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.Start
                        ){
                            Text(text = item.productName, style = TextStyle(
                                fontSize = 20.sp, fontWeight = FontWeight.W500
                            ))
                            Row() {
                                Text(text = "Quantity:", style = TextStyle(
                                    fontSize = 12.sp,
                                ))
                                Text(text = "${item.quantity}", style = TextStyle(
                                    fontSize = 12.sp, fontWeight = FontWeight.W700
                                ))
                            }

                            Row() {
                                Text(text = "Time:", style = TextStyle(fontSize = 12.sp, ))
                                Text(text = "${item.productTime}", style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.W700))
                            }


                        }

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                ) {
                            Image(
                                modifier = Modifier.weight(1f),
                                painter = R.drawable.biskut.resourceImage(),
                                contentDescription = ""
                            )
                        }
                    }
                    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(color = Color(0xffFFEB56)),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "View Details",
                            modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp), style = TextStyle(
                                fontSize = 14.sp, fontWeight = FontWeight.W700
                            )
                        )
                        IconButton(
                            modifier = Modifier
                                .size(36.dp)
                                .padding(vertical = 5.dp, horizontal = 10.dp),
                            onClick = {  }
                                ) {
                                    Icon(
                                        painter = R.drawable.leftarrow.resourceImage(),
                                        tint = Color.Black,
                                        contentDescription = "")
                                }
                        
                    }

                }
            }
            

        }
    }
}

@Composable
fun RecentTextSection(viewModel: DashboardViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 5.dp),
            text = "Recent Productions",
            color = Color.Black,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.W500
            )

            )
    }
}

@Composable
fun StatusSection(viewModel: DashboardViewModel) {
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

       Text(
           modifier = Modifier
               .padding(horizontal = 5.dp)
               .weight(2f),
           text = "Today Status",
           color = Color.Black,
           style = TextStyle(
              fontSize = 18.sp,
               fontWeight = FontWeight.Bold

           )

       )
        
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp)
        ) {
           Text(
               text = "Plant:1",
               color = Color.Red
           )
            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .height(25.dp)
                    .width(1.dp)
            )
            Text(modifier = Modifier.padding(horizontal = 5.dp),
                text = "Shift:A",
                color = Color.Green

            )
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
                tint = Color.Black,
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
                tint = Color.Black,
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
