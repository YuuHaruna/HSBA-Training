package com.beetech.hsba.ui

import com.beetech.hsba.base.BaseViewModel
import com.beetech.hsba.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(var repo: Repository) : BaseViewModel() {

}
