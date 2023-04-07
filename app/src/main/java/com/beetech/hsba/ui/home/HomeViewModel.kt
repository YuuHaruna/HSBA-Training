package com.beetech.hsba.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beetech.hsba.base.BaseViewModel
import com.beetech.hsba.base.entity.BaseObjectResponse
import com.beetech.hsba.entity.LoginResponse
import com.beetech.hsba.extension.ObjectResponse
import com.beetech.hsba.extension.backgroundThread
import com.beetech.hsba.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    private val _loginStateFlow = MutableLiveData<BaseObjectResponse<LoginResponse>>()
    val loginStateFlow: LiveData<BaseObjectResponse<LoginResponse>> get() = _loginStateFlow

    val email = MutableLiveData("")
    val password = MutableLiveData("")
    fun login(){
        mDisposable.add(
            repository.login(if(email.value.isNullOrEmpty()) "demo1@example.com" else email.value!!, if(password.value.isNullOrEmpty()) "Abcd!234" else password.value!! )
//            repository.login("demo1@example.com", "Abcd!234")
                .delay(200, TimeUnit.MILLISECONDS)
                .backgroundThread()
                .doOnSubscribe {
                    _loginStateFlow.value = BaseObjectResponse<LoginResponse>().loading()
                }
                .subscribe({ response ->
                    _loginStateFlow.value = response
                },
                    { t ->
                        _loginStateFlow.value = BaseObjectResponse<LoginResponse>().error(t)
                    })
        )
    }

}