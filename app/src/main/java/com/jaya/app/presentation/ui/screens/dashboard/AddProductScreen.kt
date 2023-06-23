package com.jaya.app.presentation.ui.screens.dashboard

import android.app.TimePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import com.jaya.app.mixing.R
import com.jaya.app.presentation.states.resourceImage
import com.jaya.app.presentation.ui.view_models.AddProductionViewModel
import com.jaya.app.presentation.ui.view_models.BaseViewModel
import java.util.*

@Composable
fun AddProductionScreen(
    baseViewModel: BaseViewModel,
    addViewModel : AddProductionViewModel  = hiltViewModel()
){

    val scaffoldState = rememberScaffoldState()


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(backgroundColor = Color(0xffFFEB56), elevation = 2.dp, title = {
                Text(
                    "Add Product",
                    style = TextStyle(
                        color = Color.Black, textAlign = TextAlign.Center, fontSize = 20.sp
                    )
                )
            },
                navigationIcon = {
                    IconButton(
                        onClick = {
                           addViewModel.appNavigator.tryNavigateBack()
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
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomEnd){
           Column(  modifier = Modifier
               .padding(paddingValues)
               .fillMaxSize())
           {
               productdetailSection(addViewModel,baseViewModel)
                
               OutlinedTextField(
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(10.dp),
                   value = addViewModel.productName.value,
                   onValueChange = addViewModel::onChangeProductName
               )

               TimeProductSection(addViewModel)
               QuantitySection(addViewModel,baseViewModel)
           }
        }
    }

}

@Composable
fun QuantitySection(
    addViewModel: AddProductionViewModel,
    baseViewModel: BaseViewModel) {
    Surface(
        modifier = Modifier.fillMaxWidth())
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
                        value = addViewModel.quantityName.value,
                        onValueChange =addViewModel::onChangeQuantityName,
                        modifier = Modifier
                            .weight(1f)
                            .size(height = 54.dp, width = 100.dp),
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

                        text = addViewModel.selectedUnit.value,
                        style = TextStyle(
                            color = Color(0xff212121),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        ),
                    )
                    IconButton(onClick = {
                        addViewModel.isIngredentsUnitExpanded.value =
                            !addViewModel.isIngredentsUnitExpanded.value
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown, contentDescription = ""
                        )
                    }
                }
            }

            DropdownMenu(expanded = addViewModel.isIngredentsUnitExpanded.value,
                onDismissRequest = { addViewModel.isIngredentsUnitExpanded.value = false }) {
                addViewModel.productDetails.value?.unit?.forEach { text ->
                    DropdownMenuItem(
                        onClick = {
                            baseViewModel.refreshLoadDataArg.value = true
                            addViewModel.quantityName.value= text.unitName
                            addViewModel.isIngredentsUnitExpanded.value = false
                        }) {
                        Text(text = text.unitName)
                    }

                }
            }
        }

    }
}

@Composable
fun TimeProductSection(addViewModel: AddProductionViewModel) {
    Row(modifier = Modifier.fillMaxWidth()) {
       SrtSection(addViewModel)
       EndSection(addViewModel) 
    }

}

@Composable
fun RowScope.EndSection(
    addViewModel: AddProductionViewModel
) {
    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    mCalendar.time = Date()

    val endTimePickerDialog = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->
            addViewModel.endTime.value = "$mHour:$mMinute"
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
                    text = addViewModel.endTime.value,
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
fun RowScope.SrtSection(
    addViewModel: AddProductionViewModel
) {
    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    mCalendar.time = Date()

    val srtTimePickerDialog = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->
            addViewModel.srtTime.value = "$mHour:$mMinute"
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
                Text(
                    modifier = Modifier
                        .padding(
                            vertical = 3.dp, horizontal = 5.dp
                        )
                        .weight(2f),
                    text = addViewModel.srtTime.value,
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
fun productdetailSection(addViewModel: AddProductionViewModel, baseViewModel: BaseViewModel) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            PlantAreaSection(addViewModel,baseViewModel)
            ShiftAreaSection(addViewModel,baseViewModel)
        }
}

@Composable
fun RowScope.ShiftAreaSection(addViewModel: AddProductionViewModel, baseViewModel: BaseViewModel) {
    Row(modifier = Modifier
        .padding(5.dp)
        .weight(1f)) {
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
            ){
                Text(modifier = Modifier.padding(
                    vertical = 3.dp, horizontal = 5.dp),
                    text = addViewModel.selectedShift.value,
                    style = TextStyle(
                        color = Color(0xff212121),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )

                IconButton(onClick = {
                    addViewModel.isExpanded.value =
                        !addViewModel.isExpanded.value
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown, contentDescription = ""
                    )
                }
            }
        }
        DropdownMenu(expanded = addViewModel.isExpanded.value,
            onDismissRequest = { addViewModel.isExpanded.value = false}) {
            addViewModel.productDetails.value?.shiftName?.forEach { text ->
                DropdownMenuItem(onClick = {
                    baseViewModel.refreshLoadDataArg.value = true
                    addViewModel.selectedShift.value = text.shiftName
                    addViewModel.isExpanded.value = false
                }) {
                    Text(text = text.shiftName)
                }
            }
        }
    }
}

@Composable
fun RowScope.PlantAreaSection(addViewModel: AddProductionViewModel, baseViewModel: BaseViewModel) {
    Row(modifier = Modifier
        .padding(5.dp)
        .weight(1f)) {
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
            ){
                Text(modifier = Modifier.padding(
                    vertical = 3.dp, horizontal = 5.dp),
                    text = addViewModel.selectedPlant.value,
                    style = TextStyle(
                        color = Color(0xff212121),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )

                IconButton(onClick = {
                    addViewModel.isMenuExpanded.value =
                        !addViewModel.isMenuExpanded.value
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown, contentDescription = ""
                    )
                }
            }
        }
        DropdownMenu(expanded = addViewModel.isMenuExpanded.value,
            onDismissRequest = { addViewModel.isMenuExpanded.value = false}) {
            addViewModel.productDetails.value?.plantName?.forEach { text ->
                DropdownMenuItem(onClick = {
                    baseViewModel.refreshLoadDataArg.value = true
                    addViewModel.selectedPlant.value = text.plantName
                    addViewModel.isExpanded.value = false
                }) {
                    Text(text = text.plantName)
                }
            }
        }
    }
}
