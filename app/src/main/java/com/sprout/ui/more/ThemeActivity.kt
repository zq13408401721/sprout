package com.sprout.ui.more

import android.content.Intent
import android.util.SparseArray
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.shop.base.BaseActivity
import com.shop.base.IItemClick
import com.sprout.BR
import com.sprout.R
import com.sprout.databinding.ActivityThemeBinding
import com.sprout.model.ThemeData
import com.sprout.ui.more.adapter.ThemeAdapter
import com.sprout.vm.more.ThemeViewModel
import kotlinx.android.synthetic.main.activity_theme.*

class ThemeActivity:BaseActivity<ThemeViewModel,ActivityThemeBinding>(R.layout.activity_theme,
    ThemeViewModel::class.java) {

    var list:MutableList<ThemeData> = mutableListOf()
    lateinit var adapter:ThemeAdapter
    lateinit var mIntent:Intent

    override fun initData() {
        mIntent = intent
        mViewModel.getTheme()
    }

    override fun initVM() {
        mViewModel.list.observe(this, Observer {
            list.clear()
            list.addAll(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun initVariable() {
    }

    override fun initView() {
        var arr =  SparseArray<Int>()
        arr.put(R.layout.layout_theme_item,BR.theme)
        adapter = ThemeAdapter(mContext,list,arr,ClickEvt())
        recy_theme.layoutManager = LinearLayoutManager(mContext)
        recy_theme.adapter = adapter
    }

    inner class ClickEvt:IItemClick<ThemeData>{
        override fun itemClick(data: ThemeData) {
            mIntent.putExtra("themeId",data.id)
            mIntent.putExtra("themeName",data.name)
            setResult(102,mIntent)
            finish()
        }

    }
}