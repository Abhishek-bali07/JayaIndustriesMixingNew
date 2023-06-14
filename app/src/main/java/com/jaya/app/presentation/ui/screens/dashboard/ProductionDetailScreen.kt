package com.jaya.app.presentation.ui.screens.dashboard

import android.app.TimePickerDialog
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import com.jaya.app.mixing.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaya.app.presentation.states.ComposeLaunchEffect
import com.jaya.app.presentation.states.resourceImage
import com.jaya.app.presentation.ui.view_models.BaseViewModel
import com.jaya.app.presentation.ui.view_models.ProductionViewModel
import java.util.*

@Composable
fun ProductionDetailScreen(
    baseViewModel: BaseViewModel,
    detailViewModel : ProductionViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(backgroundColor = Color(0xffFFEB56), elevation = 2.dp, title = {
                Text(
                    "${detailViewModel.productDetails.value?.productName}",
                    style = TextStyle(
                        color = Color.Black, textAlign = TextAlign.Center, fontSize = 20.sp
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
        }
    ) {
       paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
                if (detailViewModel.productDetails.value?.productName?.isNotEmpty() == true){

                    productdataSection(detailViewModel,baseViewModel)

                    OutlinedTextField(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        value = detailViewModel.productName.value,
                        onValueChange = detailViewModel::onChangeProductName,
                    )

                    ProductTimeSection(detailViewModel)
                    ProductQuantitySection(detailViewModel,baseViewModel)



                }
        }
    }
    BackHandler {
        baseViewModel.appNavigator.tryNavigateBack(inclusive = true)
    }
    baseViewModel.productionDataLoadArg.ComposeLaunchEffect(intentionalCode = {
        if (it.isNotEmpty()){
            detailViewModel.initialData(it)
        }
    }) {""}

}

@Composable
fun ProductQuantitySection(
    detailViewModel: ProductionViewModel,
    baseViewModel: BaseViewModel
) {

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(5.dp)

    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth().height(50.dp)
                .padding(horizontal = 5.dp)
                .border(border = BorderStroke(width = 2.dp, color = Color(0xffB9B9B9)))
                .background(color = Color.White.copy(alpha = .5f))
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(modifier = Modifier.padding(
                    vertical = 3.dp, horizontal = 5.dp),
                    text ="Do",
                   // text = detailViewModel.selectedUnit.value,
                    style = TextStyle(
                        color = Color(0xff212121),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )
                IconButton(onClick = {
                    detailViewModel.isUnitExpanded.value = !detailViewModel.isUnitExpanded.value
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = ""
                    )
                }
            }
        }

        DropdownMenu(expanded = detailViewModel.isUnitExpanded.value,
            onDismissRequest = { detailViewModel.isUnitExpanded.value = false }) {
            detailViewModel.productDetails.value?.unit?.forEach { text ->
                DropdownMenuItem(
                    onClick = {
                        baseViewModel.refreshLoadDataArg.value = true
                        detailViewModel.selectedUnit.value = text.unitsName
                        detailViewModel.isUnitExpanded.value = false
                    }) {
                    Text(text = text.unitsName)
                }

            }
        }
    }




}







@Composable
fun ProductTimeSection(detailViewModel: ProductionViewModel) {
    Row(modifier = Modifier.fillMaxWidth()) {


        StartTimeSection(detailViewModel)
        EndTimeSection(detailViewModel)

    }

}

@Composable
fun  RowScope.EndTimeSection(
    detailViewModel: ProductionViewModel
) {
    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    mCalendar.time = Date()

    val endTimePickerDialog = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->
            detailViewModel.endTime.value = "$mHour:$mMinute"
        }, mHour, mMinute, false
    )




    Row(modifier = Modifier
        .padding(5.dp)
        .weight(1f)) {

        Surface(modifier = Modifier
            .size(width = 200.dp, height = 50.dp)
            .padding(horizontal = 5.dp)
            .border(border = BorderStroke(width = 2.dp, color = Color(0xffB9B9B9)))
            .background(color = Color.White.copy(alpha = .5f))) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(modifier = Modifier.padding(
                    vertical = 3.dp, horizontal = 5.dp).weight(2f),
                    text = detailViewModel.endTime.value,
                    style = TextStyle(
                        color = Color(0xff212121),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )

                IconButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        endTimePickerDialog.show()
                    }) {
                    Icon(painter = R.drawable.clock.resourceImage(), contentDescription = null)
                }
            }
        }

    }
}

@Composable
fun  RowScope.StartTimeSection(
    detailViewModel: ProductionViewModel
) {



    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    mCalendar.time = Date()


    val srtTimePickerDialog = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->
            detailViewModel.srtTime.value = "$mHour:$mMinute"
        }, mHour, mMinute, false
    )


    Row(
        modifier = Modifier
            .padding(5.dp)
            .weight(1f)) {

        Surface(modifier = Modifier
            .size(width = 200.dp, height = 50.dp)
            .padding(horizontal = 5.dp)
            .border(border = BorderStroke(width = 2.dp, color = Color(0xffB9B9B9)))
            .background(color = Color.White.copy(alpha = .5f))) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween                   ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(modifier = Modifier.padding(
                    vertical = 3.dp, horizontal = 5.dp).weight(2f),
                    text = detailViewModel.srtTime.value,
                    style = TextStyle(
                        color = Color(0xff212121),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )


                IconButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        srtTimePickerDialog.show()
                    }) {
                    Icon(painter = R.drawable.clock.resourceImage(), contentDescription = null)
                }
            }


        }

    }
}




@Composable
fun productdataSection(detailViewModel: ProductionViewModel, baseViewModel: BaseViewModel) {
    Row(
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
            .padding(5.dp)
            .weight(1f)
    ) {
        Surface(
            modifier = Modifier
                .size(width = 200.dp, height = 50.dp)
                .padding(horizontal = 5.dp)
                .border(border = BorderStroke(width = 2.dp, color = Color(0xffB9B9B9)))
                .background(color = Color.White.copy(alpha = .5f))
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween                   ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(modifier = Modifier.padding(
                    vertical = 3.dp, horizontal = 5.dp),
                    text = detailViewModel.selectedShift.value,
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
                        detailViewModel.selectedShift.value = text.shiftName
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
            .padding(5.dp)
            .weight(1f)
    ) {
        Surface(
            modifier = Modifier
                .size(width = 200.dp, height = 50.dp)
                .padding(horizontal = 5.dp)
                .border(border = BorderStroke(width = 2.dp, color = Color(0xffB9B9B9)))
                .background(color = Color.White.copy(alpha = .5f))
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(modifier = Modifier.padding(
                    vertical = 3.dp, horizontal = 5.dp),
                    text = detailViewModel.selectedPlant.value,
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
                        detailViewModel.selectedPlant.value = text.plantName
                        detailViewModel.isMenuExpanded.value = false
                    }) {
                    Text(text = text.plantName)
                }

            }
        }
    }
}
