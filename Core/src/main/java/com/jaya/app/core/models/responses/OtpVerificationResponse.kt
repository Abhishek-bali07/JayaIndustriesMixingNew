package com.jaya.app.core.models.responses

import com.google.gson.annotations.SerializedName

data class OtpVerificationResponse(

    val status: Boolean,
    val message: String,
    val isVerified: Boolean,
    val userId: String
)
