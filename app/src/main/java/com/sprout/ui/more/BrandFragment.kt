package com.sprout.ui.more

import android.util.SparseArray
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.shop.base.BaseFragment
import com.shop.base.IItemClick
import com.sprout.BR
import com.sprout.R
import com.sprout.databinding.FragmentBrandBinding
import com.sprout.model.BrandData
import com.sprout.ui.more.adapter.BrandAdapter
import com.sprout.vm.more.BrandViewModel
import kotlinx.android.synthetic.main.fragment_brand.*

/**
 * 品牌的显示列表页面
 */
class BrandFragment(
        var type:Int
):BaseFragment<BrandViewModel,FragmentBrandBinding>(R.layout.fragment_brand,BrandViewModel::class.java) {

    lateinit var brandList:MutableList<BrandData.Data>
    lateinit var brandAdapter:BrandAdapter

    override fun initData() {
        mViewModel.getBrand()
    }

    override fun initVM() {
        mViewModel.bList.observe(this, Observer {
            brandList.clear()
            brandList.addAll(it.data)
            brandAdapter.notifyDataSetChanged()
        })
    }

    override fun initVariable() {

    }

    override fun initView() {
        var brandArr = SparseArray<Int>()
        brandArr.put(R.layout.layout_brand_item, BR.brandData)
        brandList = mutableListOf()
        recy_brand.layoutManager = LinearLayoutManager(mContext)
        brandAdapter = BrandAdapter(mContext,brandList,brandArr,BrandClick())
        recy_brand.adapter = brandAdapter
    }

    inner class BrandClick: IItemClick<BrandData.Data> {
        override fun itemClick(data: BrandData.Data) {
            //传递数据到MoreEditorActivity页面
            mActivity.intent.putExtra("name",data.name)
            mActivity.intent.putExtra("id",data.id)
            mActivity.setResult(1,mActivity.intent)
            mActivity.finish()
        }

    }
}