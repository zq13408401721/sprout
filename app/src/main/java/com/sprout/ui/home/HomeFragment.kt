package com.sprout.ui.home

import android.util.TypedValue
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.shop.base.BaseFragment
import com.sprout.R
import com.sprout.databinding.FragmentHomeBinding
import com.sprout.vm.home.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.singleLine

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
        tab_home.addOnTabSelectedListener(object:OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                var txtView = TextView(activity)
                var fontSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,20f,resources.displayMetrics)
                txtView.setTextSize(TypedValue.COMPLEX_UNIT_SP,fontSize)
                txtView.setTextColor(resources.getColor(R.color.col_tab_select))
                txtView.setText(tab!!.text)
                txtView.singleLine = true
                tab.customView = txtView
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab!!.customView = null
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
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