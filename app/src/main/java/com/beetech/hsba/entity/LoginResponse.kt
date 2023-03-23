package com.beetech.hsba.entity

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("accessToken")
    val accessToken: String? = "",
    @SerializedName("address")
    val address: String? = "",
    @SerializedName("apple_id")
    val appleId: String? = "",
    @SerializedName("avatar")
    val avatar: String = "",
    @SerializedName("birthday")
    val birthday: String? = "",
    @SerializedName("check_login_first")
    val checkLoginFirst: Int? = 0,
    @SerializedName("check_user")
    val checkUser: Any? = "",
    @SerializedName("created_at")
    val createdAt: String? = "",
    @SerializedName("deleted_at")
    val deletedAt: String? = "",
    @SerializedName("email")
    val email: String? = "",
    @SerializedName("facebook_id")
    val facebookId: String? = "",
    @SerializedName("gender")
    val gender: Int? = 0,
    @SerializedName("google_id")
    val googleId: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("login_type")
    val loginType: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("nurse_id")
    val nurseId: Int? = 0,
    @SerializedName("phone")
    val phone: String? = "",
    @SerializedName("status")
    val status: Int? = 0,
    @SerializedName("typeAccount")
    val typeAccount: Int? = 0,
    @SerializedName("updated_at")
    val updatedAt: String? = "",
    @SerializedName("username")
    val username: String? = "",
    @SerializedName("zalo_id")
    val zaloId: String? = ""
)