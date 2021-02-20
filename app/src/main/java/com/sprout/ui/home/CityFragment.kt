package com.sprout.ui.home

import android.util.SparseArray
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shop.base.BaseFragment
import com.shop.base.IItemClick
import com.sprout.BR
import com.sprout.R
import com.sprout.databinding.FragmentCityBinding
import com.sprout.model.TrendsData
import com.sprout.ui.home.adapter.CityTrendsAdpater
import com.sprout.ui.mine.MineFragment
import com.sprout.vm.home.CityViewModel
import com.sprout.widget.ListDecoration
import kotlinx.android.synthetic.main.fragment_city.*

class CityFragment(var command:Int):BaseFragment<CityViewModel,FragmentCityBinding>(R.layout.fragment_city,CityViewModel::class.java) {

    lateinit var manager:StaggeredGridLayoutManager
    lateinit var cityTrendsAdapter:CityTrendsAdpater
    var list:MutableList<TrendsData> = mutableListOf()
    companion object{
        // command 1 城市
        val instance: CityFragment by lazy { CityFragment(1) }
    }

    override fun initData() {
        mViewModel.getTrends(command)
    }

    override fun initVM() {
        mViewModel.list.observe(this, Observer {
            list.clear()
            list.addAll(it)
            cityTrendsAdapter.notifyDataSetChanged()
        })
    }

    override fun initVariable() {
    }

    override fun initView() {
        manager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recy_city.layoutManager = manager
        var arr = SparseArray<Int>()
        arr.put(R.layout.layout_city_trends_item,BR.cityTrendsData)
        cityTrendsAdapter = CityTrendsAdpater(mContext,list,arr,ItemClick())
        recy_city.addItemDecoration(ListDecoration())
        recy_city.adapter = cityTrendsAdapter

    }

    inner class ItemClick:IItemClick<TrendsData>{
        override fun itemClick(data: TrendsData) {
            TODO("Not yet implemented")
        }

    }
}