package com.jaya.app.presentation.ui.screens.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import com.jaya.app.mixing.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaya.app.presentation.states.resourceImage
import com.jaya.app.presentation.ui.view_models.BaseViewModel
import com.jaya.app.presentation.ui.view_models.ProductionViewModel

@Composable
fun ProductionDetailScreen(
    baseViewModel: BaseViewModel,
    detailViewModel : ProductionViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()
    Scaffold(scaffoldState = scaffoldState) {
       paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
                if (detailViewModel.productDetails.value?.productName?.isNotEmpty() == true){
                    TopAppBar(backgroundColor = Color(0xffFFEB56), elevation = 2.dp, title = {
                        Text(
                            "${detailViewModel.productDetails.value?.productName}",
                            style = TextStyle(
                                color = Color.White, textAlign = TextAlign.Center, fontSize = 20.sp
                            )
                        )
                    },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    detailViewModel.appNavigator.tryNavigateBack()
                                }) {
                                Image(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .padding(horizontal = 8.dp),
                                    painter = R.drawable.backarrow.resourceImage(),
                                    contentDescription = null
                                )
                            }

                        })

                    productdataSection(detailViewModel,baseViewModel)
                }
        }
    }

}
@Composable
fun productdataSection(detailViewModel: ProductionViewModel, baseViewModel: BaseViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {


        ShiftSection(detailViewModel,baseViewModel)
        PlantSection(detailViewModel,baseViewModel)

    }
}

@Composable
fun RowScope.ShiftSection(detailViewModel: ProductionViewModel, baseViewModel: BaseViewModel) {

    Row(
        modifier = Modifier
        .weight(1f)
    ) {
        Surface(
            modifier = Modifier
                .size(width = 110.dp, height = 30.dp)
                .padding(horizontal = 5.dp)
                .background(color = Color.White.copy(alpha = .5f))
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(modifier = Modifier.padding(
                    vertical = 3.dp, horizontal = 5.dp),
                    text = detailViewModel.lSelectedText.value,
                    style = TextStyle(
                        color = Color(0xff212121),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )
                IconButton(onClick = {
                    detailViewModel.isExpanded.value =
                        !detailViewModel.isExpanded.value
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown, contentDescription = ""
                    )
                }
            }
        }

        DropdownMenu(expanded = detailViewModel.isExpanded.value,
            onDismissRequest = { detailViewModel.isExpanded.value = false }) {
            detailViewModel.productDetails.value?.shiftName?.forEach { text ->
                DropdownMenuItem(
                    onClick = {
                        baseViewModel.refreshLoadDataArg.value = true
                        detailViewModel.lSelectedText.value = text.shiftName
                        detailViewModel.isExpanded.value = false
                    }) {
                    Text(text = text.shiftName)
                }

            }
        }
    }

}

@Composable
fun RowScope.PlantSection(detailViewModel: ProductionViewModel, baseViewModel: BaseViewModel) {
    Row(
        modifier = Modifier
            .weight(1f)
    ) {
        Surface(
            modifier = Modifier
                .size(width = 110.dp, height = 30.dp)
                .padding(horizontal = 5.dp)
                .background(color = Color.White.copy(alpha = .5f))
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(modifier = Modifier.padding(
                    vertical = 3.dp, horizontal = 5.dp),
                    text = detailViewModel.mSelectedText.value,
                    style = TextStyle(
                        color = Color(0xff212121),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )
                IconButton(onClick = {
                    detailViewModel.isMenuExpanded.value =
                        !detailViewModel.isMenuExpanded.value
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown, contentDescription = ""
                    )
                }
            }
        }

        DropdownMenu(expanded = detailViewModel.isMenuExpanded.value,
            onDismissRequest = { detailViewModel.isMenuExpanded.value = false }) {
            detailViewModel.productDetails.value?.plantName?.forEach { text ->
                DropdownMenuItem(
                    onClick = {
                        baseViewModel.refreshLoadDataArg.value = true
                        detailViewModel.mSelectedText.value = text.plantName
                        detailViewModel.isMenuExpanded.value = false
                    }) {
                    Text(text = text.plantName)
                }

            }
        }
    }
}
