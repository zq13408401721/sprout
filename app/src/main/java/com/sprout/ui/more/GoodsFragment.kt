package com.sprout.ui.more

import android.util.SparseArray
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.shop.base.BaseFragment
import com.shop.base.BaseViewModel
import com.shop.base.IItemClick
import com.sprout.BR
import com.sprout.R
import com.sprout.databinding.FragmentGoodsBinding
import com.sprout.model.GoodData
import com.sprout.ui.more.adapter.GoodAdapter
import com.sprout.vm.more.GoodsViewModel
import kotlinx.android.synthetic.main.fragment_goods.*

class GoodsFragment(
        var type:Int
):BaseFragment<GoodsViewModel,FragmentGoodsBinding>(R.layout.fragment_goods,GoodsViewModel::class.java) {

    lateinit var goodList:MutableList<GoodData.Data>
    lateinit var goodAdapter: GoodAdapter

    override fun initData() {
        mViewModel.getGood()
    }

    override fun initVM() {
        mViewModel.gList.observe(this, Observer {
            goodList.clear()
            goodList.addAll(it.data)
            goodAdapter.notifyDataSetChanged()
        })
    }

    override fun initVariable() {
    }


    override fun initView() {
        var goodArr = SparseArray<Int>()
        goodArr.put(R.layout.fragment_goods, BR.goodData)
        goodList = mutableListOf()
        recy_goods.layoutManager = LinearLayoutManager(mContext)
        goodAdapter = GoodAdapter(mContext,goodList,goodArr,GoodClick())

    }

    inner class GoodClick: IItemClick<GoodData.Data> {
        override fun itemClick(data: GoodData.Data) {
            mActivity.intent.putExtra("name",data.name)
            mActivity.intent.putExtra("id",data.id)
            mActivity.setResult(2,mActivity.intent)
        }

    }
}