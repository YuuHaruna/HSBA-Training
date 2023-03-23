package com.beetech.hsba.ui.home

import com.beetech.hsba.entity.SSOPlatform

interface OnClickSSO {
    fun loginSSO(type: SSOPlatform)
}
