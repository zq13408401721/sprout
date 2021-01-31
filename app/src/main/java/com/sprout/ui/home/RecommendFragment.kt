package com.sprout.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.shop.base.BaseFragment
import com.sprout.R
import com.sprout.databinding.FragmentRecommendBinding
import com.sprout.vm.home.RecommendViewModel
import kotlinx.android.synthetic.main.fragment_recommend.*

class RecommendFragment(var command:Int):BaseFragment<RecommendViewModel,FragmentRecommendBinding>(R.layout.fragment_recommend,RecommendViewModel::class.java) {

    var list:MutableList<TrendsFragment> = mutableListOf()
    var titles:MutableList<String> = mutableListOf()
    lateinit var trendsFragmentAdapter:TrendsFragmentAdapter

    companion object{
        val instance: RecommendFragment by lazy { RecommendFragment(3) }
    }

    override fun initData() {
        mViewModel.getChannel()
    }

    override fun initVM() {
        mViewModel.channels.observe(this, Observer {
            for(i in 0 until it.size){
                tab_channel.addTab(tab_channel.newTab().setText(it.get(i).name))
                var fragment = TrendsFragment(command,it.get(i).id)
                list.add(fragment)
                titles.add(it.get(i).name)
                trendsFragmentAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun initVariable() {
    }

    override fun initView() {
        trendsFragmentAdapter = childFragmentManager?.let { TrendsFragmentAdapter(it) }!!
        viewPager.adapter = trendsFragmentAdapter
        tab_channel.setupWithViewPager(viewPager)
        tab_channel.tabMode = TabLayout.MODE_SCROLLABLE
    }

    inner class TrendsFragmentAdapter(fm:FragmentManager):FragmentPagerAdapter(fm){
        override fun getCount(): Int {
            return list.size
        }

        override fun getItem(position: Int): Fragment {
            return list.get(position)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles.get(position)
        }
    }
}