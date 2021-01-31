package com.sprout.ui.home

import android.util.SparseArray
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shop.base.BaseFragment
import com.shop.base.IItemClick
import com.sprout.BR
import com.sprout.R
import com.sprout.databinding.FragmentTrendsBinding
import com.sprout.model.TrendsData
import com.sprout.ui.home.adapter.TrendsAdapter
import com.sprout.vm.home.TrendsViewModel
import kotlinx.android.synthetic.main.fragment_trends.*
import com.sprout.widget.ListDecoration

class TrendsFragment(var command:Int,var channelId:Int):BaseFragment<TrendsViewModel,FragmentTrendsBinding>(R.layout.fragment_trends,TrendsViewModel::class.java) {

    lateinit var trendsAdapter:TrendsAdapter
    var list:MutableList<TrendsData> = mutableListOf()
    lateinit var manager:StaggeredGridLayoutManager

    override fun initData() {
        mViewModel.getTrendsList(command,channelId)
    }

    override fun initVM() {
        mViewModel.list.observe(this, Observer {
            list.clear()
            list.addAll(it)
            trendsAdapter.notifyDataSetChanged()
        })
    }

    override fun initVariable() {
    }

    override fun initView() {
        var arr = SparseArray<Int>()
        arr.put(R.layout.layout_trends_item, BR.trendsData)
        trendsAdapter = TrendsAdapter(mContext,list,arr,ItemClick())
        manager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recy_trends.layoutManager = manager
        recy_trends.adapter = trendsAdapter
        recy_trends.addItemDecoration(ListDecoration())

    }

    /**
     * 条目点击
     */
    inner class ItemClick:IItemClick<TrendsData>{
        override fun itemClick(data: TrendsData) {

        }

    }
}