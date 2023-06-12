package com.jaya.app.core.entities

data class ProductionDetailData(
    val productName : String,
    val plantName:List<Plant>,
    val shiftName: List<Shift>,
    val startTime:String,
    val endTime:String,
    val productQty: String,
    val unit:List<Units>,
    val ingredients:List<Ingredents>
)
