package com.jaya.app.core.models.responses


import com.google.gson.annotations.SerializedName
import com.jaya.app.core.entities.AppVersion



data class AppVersionResponse(
    val status: Boolean,
    val message: String,
    val appVersion: AppVersion
)