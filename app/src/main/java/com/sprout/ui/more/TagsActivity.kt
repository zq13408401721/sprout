package com.sprout.ui.more

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.tabs.TabLayout
import com.shop.base.BaseActivity
import com.shop.base.BaseFragment
import com.shop.base.IItemClick
import com.sprout.BR
import com.sprout.R
import com.sprout.databinding.ActivityTagsBinding
import com.sprout.model.BrandData
import com.sprout.model.GoodData
import com.sprout.ui.more.adapter.BrandAdapter
import com.sprout.ui.more.adapter.GoodAdapter
import com.sprout.vm.more.TagsViewModel
import kotlinx.android.synthetic.main.activity_tags.*
import kotlinx.android.synthetic.main.fragment_goods.*

/**
 * 用来显示tags标签列表的界面
 */
class TagsActivity:BaseActivity<TagsViewModel,ActivityTagsBinding>(R.layout.activity_tags,TagsViewModel::class.java) {

    var fragments:MutableList<Fragment> = mutableListOf()
    var titles:List<String> = listOf("品牌","商品","地址")

    override fun initData() {

    }

    override fun initVM() {

    }

    override fun initVariable() {

    }

    /**
     * 初始化标签对应的页面
     */
    override fun initView() {
        fragments.add(BrandFragment(1))
        fragments.add(GoodsFragment(2))
        fragments.add(AdressFragment(3))
        tab_tags.setupWithViewPager(viewPager)
        tab_tags.addTab(tab_tags.newTab())
        tab_tags.addTab(tab_tags.newTab())
        tab_tags.addTab(tab_tags.newTab())
        viewPager.adapter = MyFragmentAdapter(supportFragmentManager)
    }

    inner class MyFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
            return fragments.size
        }

        override fun getItem(position: Int): Fragment {
            return fragments.get(position)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position].toString()
        }



    }
}