package com.beetech.hsba.base.entity

import com.google.gson.annotations.SerializedName

open class BaseResponse{
    @SerializedName("status") val status: Int? = null
    @SerializedName("msg") val msg: String? = null
}