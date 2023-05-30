package com.jaya.app.core.entities.responses

import com.google.gson.annotations.SerializedName
import com.jaya.app.core.entities.AppVersion


data class AppVersionResponse(
    @SerializedName("success")
    val status: Boolean,
    val message: String,
    val appVersion: AppVersion
)