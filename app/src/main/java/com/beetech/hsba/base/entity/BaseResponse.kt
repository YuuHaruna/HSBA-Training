package com.beetech.hsba.base.entity

import com.google.gson.annotations.SerializedName

open class BaseResponse{
    @SerializedName("code") val code: Int? = null
    @SerializedName("msg") val msg: String? = null
}