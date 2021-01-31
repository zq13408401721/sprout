package com.sprout.vm.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.shop.base.BaseViewModel
import com.sprout.net.InJection
import com.sprout.net.SproutRepository
import com.sprout.ui.home.CityFragment
import com.sprout.ui.home.EnjoyFragment
import com.sprout.ui.home.RecommendFragment

class HomeViewModel:BaseViewModel<SproutRepository>(InJection.repository) {

    var fragments:MutableLiveData<List<Fragment>> = MutableLiveData()

    /**
     * 初始化fragment
     */
    fun addFragments(){
        var list:MutableList<Fragment> = mutableListOf()
        list.add(CityFragment.instance)
        list.add(EnjoyFragment.instance)
        list.add(RecommendFragment.instance)
        fragments.postValue(list)
    }

}