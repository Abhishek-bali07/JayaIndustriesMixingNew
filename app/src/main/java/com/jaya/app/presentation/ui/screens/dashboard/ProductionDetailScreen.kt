package com.jaya.app.presentation.ui.screens.dashboard

import android.app.TimePickerDialog
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaya.app.mixing.R
import com.jaya.app.presentation.states.ComposeLaunchEffect
import com.jaya.app.presentation.states.resourceImage
import com.jaya.app.presentation.states.resourceString
import com.jaya.app.presentation.ui.custom_composable.AppButton
import com.jaya.app.presentation.ui.custom_composable.SaveButton
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
        Box(
            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomEnd,
        ) {
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
                    MixingIngredentSection(detailViewModel, baseViewModel)
                    ButtonSection(detailViewModel)




                }
            }
            SaveButton(
                enable = detailViewModel.enableBtn.value,
                loading = detailViewModel.mixingLoading.value,
                action = detailViewModel::uploadMixingData,
                name =R.string.save )
        }

    }
    BackHandler {
        baseViewModel.appNavigator.tryNavigateBack(inclusive = true)
    }
    baseViewModel.productionDataLoadArg.ComposeLaunchEffect(intentionalCode = {
        if (it.isNotEmpty()){
            detailViewModel.initialData()
        }
    }) {""}

}

@Composable
fun ButtonSection(detailViewModel: ProductionViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth()) {


    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MixingIngredentSection(
    detailViewModel: ProductionViewModel,
    baseViewModel: BaseViewModel
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = R.string.mixing.resourceString(), style = TextStyle(
                fontWeight = FontWeight.W500,
                fontSize = 20.sp
            ))



            Surface(modifier = Modifier
                .size(width = 100.dp, height = 30.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(color = Color(0xff68B560)),
                onClick = detailViewModel::addIngredient
            ) {
                Row(
                    modifier = Modifier.background(color =  Color(0xff68B560)),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(
                            vertical = 3.dp, horizontal = 5.dp
                        ),
                        text = "Add",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        ),
                    )
                    Icon(
                        modifier = Modifier
                            .background(color = Color(0xff68B560))
                            .size(20.dp),
                        painter = R.drawable.plus.resourceImage(),
                        contentDescription = "null", tint = Color.White
                    )


                }

            }


        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp),
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 10.dp)
                    .size(height = 54.dp, width = 100.dp),
                value =detailViewModel.ingredentName.value ,
                onValueChange = detailViewModel::onChangeIngredentName)

            Surface(
                modifier = Modifier.weight(1f))
            {
                Row(
                    modifier = Modifier
                        .padding(5.dp)

                ) {
                    Surface(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .height(55.dp)
                            .border(border = BorderStroke(width = 2.dp, color = Color(0xffB9B9B9)))
                            .background(color = Color.White.copy(alpha = .5f))

                    ) {
                        Row(
                            horizontalArrangement = Arrangement.End                   ,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                modifier = Modifier
                                    .weight(1f)
                                    .size(height = 54.dp, width = 100.dp),
                                value =detailViewModel.ingredentQtty.value ,
                                onValueChange = detailViewModel::onChangeIngredentQtty,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color(0xffB9B9B9),
                                    unfocusedBorderColor = Color(0xffB9B9B9)
                                ))
                            Divider(
                                color = Color(0xffB9B9B9),
                                modifier = Modifier
                                    .fillMaxHeight()  //fill the max height
                                    .width(2.dp)
                            )

                            Text(
                                modifier = Modifier.padding(
                                    vertical = 3.dp, horizontal = 5.dp),

                                text = detailViewModel.ingredentUnit.value,
                                style = TextStyle(
                                    color = Color(0xff212121),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                            )
                            IconButton(onClick = {
                                detailViewModel.isIngredentsUnitExpanded.value =
                                    !detailViewModel.isIngredentsUnitExpanded.value
                            }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown, contentDescription = ""
                                )
                            }
                        }
                    }

                    DropdownMenu(expanded = detailViewModel.isIngredentsUnitExpanded.value,
                        onDismissRequest = { detailViewModel.isIngredentsUnitExpanded.value = false }) {
                        detailViewModel.productDetails.value?.unit?.forEach { text ->
                            DropdownMenuItem(
                                onClick = {
                                    baseViewModel.refreshLoadDataArg.value = true
                                    detailViewModel.ingredentUnit.value = text.unitName
                                    detailViewModel.isIngredentsUnitExpanded.value = false
                                }) {
                                Text(text = text.unitName)
                            }

                        }
                    }
                }

            }
        }

        LazyColumn( modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top){
            items(detailViewModel.addIngredents){item ->
                Card(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(12.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        if (item != null) {
                            Column() {
                                Row() {
                                    Text(text = "IngredentName:", style = TextStyle(fontWeight = FontWeight.W700))
                                    Text(text ="${item.ingName}")
                                }

                                Row(){
                                    Text(text = "IngredentQuantity:", style = TextStyle(fontWeight = FontWeight.W700))
                                    Text(text = "${item.ingQtty}${item.selectedUnit}")
                                }
                            }

                            IconButton(
                                onClick = {
                                    detailViewModel.removeIngredient(item)
                                }
                            ) {
                                Icon(
                                    painter = R.drawable.mcircle.resourceImage(),
                                    contentDescription = null
                                )
                            }
                            

                        }
                    }
                }
            }

        }
    }



}

@Composable
fun ProductQuantitySection(
    detailViewModel: ProductionViewModel,
    baseViewModel: BaseViewModel
) {
    Surface(modifier = Modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 10.dp,
                    vertical = 10.dp
                ))
        {

            OutlinedTextField(modifier = Modifier.height(55.dp),
                value = detailViewModel.quantityName.value,
                onValueChange =detailViewModel::onChangeQuantityName,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xffB9B9B9),
                    unfocusedBorderColor = Color(0xffB9B9B9)
                )
            )

            Row(
                modifier = Modifier
                    .padding(5.dp)

            ) {
                Surface(

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .border(border = BorderStroke(width = 2.dp, color = Color(0xffB9B9B9)))
                        .background(color = Color.White.copy(alpha = .5f))

                ) {
                    Row(
                        horizontalArrangement = Arrangement.End                   ,
                        verticalAlignment = Alignment.CenterVertically
                    ) {


                        Text(
                            modifier = Modifier.padding(
                                vertical = 3.dp, horizontal = 5.dp),

                            text = detailViewModel.selectedUnit.value,
                            style = TextStyle(
                                color = Color(0xff212121),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            ),
                        )
                        IconButton(onClick = {
                            detailViewModel.isUnitExpanded.value =
                                !detailViewModel.isUnitExpanded.value
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown, contentDescription = ""
                            )
                        }
                    }
                }

                DropdownMenu(expanded = detailViewModel.isUnitExpanded.value,
                    onDismissRequest = { detailViewModel.isUnitExpanded.value = false },

                    ) {
                    detailViewModel.productDetails.value?.unit?.forEach { text ->
                        DropdownMenuItem(
                            onClick = {
                                baseViewModel.refreshLoadDataArg.value = true
                                detailViewModel.selectedUnit.value = text.unitName
                                detailViewModel.isUnitExpanded.value = false
                            }) {
                            Text(text = text.unitName)
                        }

                    }
                }
            }
        }
    }




   /* Row(
        modifier = Modifier
            .padding(5.dp)

    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 5.dp)
                .border(border = BorderStroke(width = 2.dp, color = Color(0xffB9B9B9)))
                .background(color = Color.White.copy(alpha = .5f))
        ) {
            Row(
                horizontalArrangement = Arrangement.End                   ,
                verticalAlignment = Alignment.CenterVertically
            ) {


                Divider(
                    color = Color(0xffB9B9B9),
                    modifier = Modifier
                        .fillMaxHeight()  //fill the max height
                        .width(2.dp)
                )
                Text(
                    modifier = Modifier.padding(
                    vertical = 3.dp, horizontal = 5.dp),

                    text = detailViewModel.selectedUnit.value,
                    style = TextStyle(
                        color = Color(0xff212121),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )
                IconButton(onClick = {
                    detailViewModel.isUnitExpanded.value =
                        !detailViewModel.isUnitExpanded.value
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown, contentDescription = ""
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
                        detailViewModel.selectedUnit.value = text.unitName
                        detailViewModel.isUnitExpanded.value = false
                    }) {
                    Text(text = text.unitName)
                }

            }
        }
    }*/
    /*Row(
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
//                    text ="Do",
                    text = detailViewModel.selectedUnit.value,
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
    }*/




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
                Text(modifier = Modifier
                    .padding(
                        vertical = 3.dp, horizontal = 5.dp
                    )
                    .weight(2f),
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(modifier = Modifier
                    .padding(
                        vertical = 3.dp, horizontal = 5.dp
                    )
                    .weight(2f),
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
