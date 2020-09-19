package com.andriiginting.core_network

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @SerializedName("status_code") var code: Int = 0,
    @SerializedName("status_message") var message: String = "",
    @SerializedName("success") var success: Boolean = false
)