package com.beetech.hsba.extension

import androidx.lifecycle.MutableLiveData
import com.beetech.hsba.R
import com.beetech.hsba.base.entity.BaseListLoadMoreResponse
import com.beetech.hsba.base.entity.BaseListResponse
import com.beetech.hsba.base.entity.BaseObjectResponse

typealias ObjectResponse<T> = MutableLiveData<BaseObjectResponse<T>>
typealias ListResponse<T> = MutableLiveData<BaseListResponse<T>>
typealias ListLoadMoreResponse<T> = MutableLiveData<BaseListLoadMoreResponse<T>>

typealias AndroidColors = android.R.color
typealias ProjectColors = R.color
