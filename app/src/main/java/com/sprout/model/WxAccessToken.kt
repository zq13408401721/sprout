package com.sprout.model

data class WxAccessToken(
    val access_token: String,
    val expires_in: Int,
    val openid: String,
    val refresh_token: String,
    val scope: String
)