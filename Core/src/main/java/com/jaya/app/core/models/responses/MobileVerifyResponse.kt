package com.jaya.app.core.models.responses

import com.google.gson.annotations.SerializedName

data class MobileVerifyResponse(
    val status: Boolean,
    val message: String,
    val isSend: Boolean,
)
