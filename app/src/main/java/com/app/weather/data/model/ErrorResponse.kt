package com.app.weather.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ErrorResponse(
    @SerializedName("cod")
    val code: Int = -1,
    @SerializedName("message")
    val message: String
)