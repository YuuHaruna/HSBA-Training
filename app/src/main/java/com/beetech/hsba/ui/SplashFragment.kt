package com.beetech.hsba.ui

import android.os.Handler
import android.os.Looper
import com.beetech.hsba.R
import com.beetech.hsba.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels
import com.beetech.hsba.ui.home.HomeFragment

@AndroidEntryPoint
class SplashFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.splash_fragment

    override fun backFromAddFragment() {
    }

    override fun backPressed(): Boolean {
        return false
    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initListener() {
        Looper.getMainLooper()?.let {
            Handler(it).postDelayed({
                getVC().replaceFragment(HomeFragment::class.java)
            },1500)
        }

    }

    private val viewModel: SplashViewModel by viewModels()
}
