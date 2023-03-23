package com.beetech.hsba.entity

import androidx.annotation.DrawableRes

data class SSOEntity(
    @DrawableRes val icon: Int,
    val platformName: String,
    val platform: SSOPlatform
)

enum class SSOPlatform{
    Zalo, Facebook, Apple, Google, Other
}