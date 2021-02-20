package com.sprout.ui.home

import com.shop.base.BaseFragment
import com.sprout.R
import com.sprout.databinding.FragmentEnjoyBinding
import com.sprout.vm.home.EnjoyViewModel

class EnjoyFragment(var command:Int):BaseFragment<EnjoyViewModel,FragmentEnjoyBinding>(R.layout.fragment_enjoy,EnjoyViewModel::class.java) {

    companion object{
        // command 2 关注
        val instance: EnjoyFragment by lazy { EnjoyFragment(2) }
    }

    override fun initData() {

    }

    override fun initVM() {
    }

    override fun initVariable() {
    }

    override fun initView() {
    }
}