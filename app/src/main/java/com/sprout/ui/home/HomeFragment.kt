package com.sprout.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.shop.base.BaseFragment
import com.sprout.R
import com.sprout.databinding.FragmentHomeBinding
import com.sprout.vm.home.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment:BaseFragment<HomeViewModel,FragmentHomeBinding>(R.layout.fragment_home,HomeViewModel::class.java) {

    companion object{
        val instance:HomeFragment by lazy { HomeFragment() }
    }

    lateinit var myFragmentAdapter:MyFragmentAdapter
    var fragments:List<Fragment> = listOf()
    var tabs = arrayListOf("城市","关注","推荐")

    override fun initData() {

    }

    override fun initVM() {

        mViewModel.fragments.observe(this, Observer {
            fragments = it
            tab_home.selectTab(tab_home.getTabAt(2))
            myFragmentAdapter.notifyDataSetChanged()
        })
        //初始添加
        mViewModel.addFragments()

    }

    override fun initVariable() {
    }

    override fun initView() {
        for(i in 0 until tabs.size){
            tab_home.addTab(tab_home.newTab().setText(tabs[i]))
        }
        myFragmentAdapter = fragmentManager?.let { MyFragmentAdapter(it) }!!
        viewPager.adapter = myFragmentAdapter
        tab_home.setupWithViewPager(viewPager)
    }

    inner class MyFragmentAdapter(fm: FragmentManager):FragmentPagerAdapter(fm){
        override fun getCount(): Int {
            return fragments.size
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabs[position]
        }

    }

}