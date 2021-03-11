package com.sprout.ui.home

import android.util.TypedValue
import android.widget.TextView
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
import org.jetbrains.anko.singleLine

class RecommendFragment(var command:Int):BaseFragment<RecommendViewModel,FragmentRecommendBinding>(R.layout.fragment_recommend,RecommendViewModel::class.java) {

    var list:MutableList<TrendsFragment> = mutableListOf()
    var titles:MutableList<String> = mutableListOf()
    lateinit var trendsFragmentAdapter:TrendsFragmentAdapter

    companion object{
        // command 3 推荐
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
        tab_channel.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                var txtView:TextView
                if(tab!!.customView == null){
                    txtView = TextView(activity)
                }else{
                    txtView = tab!!.customView as TextView
                }
                var fontSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,16f,resources.displayMetrics)
                txtView.setTextSize(TypedValue.COMPLEX_UNIT_SP,fontSize)
                txtView.setTextColor(resources.getColor(R.color.col_tab_select))
                txtView.setText(tab!!.text)
                txtView.singleLine = true
                tab.customView = txtView
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                if(tab!!.customView != null){
                    var fontSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,14f,resources.displayMetrics)
                    var txtView = tab!!.customView as TextView
                    txtView.setTextSize(TypedValue.COMPLEX_UNIT_SP,fontSize)
                    txtView.setTextSize(fontSize)
                    txtView.setTextColor(resources.getColor(R.color.col_tab_unselect))
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
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