package com.beetech.hsba.ui.home

import com.beetech.hsba.R
import com.beetech.hsba.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var viewModel: HomeViewModel


    override fun backFromAddFragment() {
        
    }

    override val layoutId: Int
        get() = R.layout.home_fragment

    override fun initView() {
        
    }

    override fun initData() {
        
    }

    override fun initListener() {
        
    }

    override fun backPressed(): Boolean {

        return true
    }
}