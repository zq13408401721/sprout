package com.sprout.ui.more

import android.content.Intent
import android.util.SparseArray
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.shop.base.BaseActivity
import com.shop.base.IItemClick
import com.sprout.BR
import com.sprout.R
import com.sprout.databinding.ActivityChannelBinding
import com.sprout.model.ChannelData
import com.sprout.ui.more.adapter.ChannelAdapter
import com.sprout.vm.more.ChannelViewModel
import kotlinx.android.synthetic.main.activity_channel.*

class ChannelActivity:BaseActivity<ChannelViewModel,ActivityChannelBinding>(R.layout.activity_channel,ChannelViewModel::class.java) {

    var list:MutableList<ChannelData> = mutableListOf()
    lateinit var adapter:ChannelAdapter
    lateinit var mIntent: Intent

    override fun initData() {
        mIntent = intent
        mViewModel.getChannel()
    }

    override fun initVM() {
        mViewModel.channels.observe(this, Observer {
            list.clear()
            list.addAll(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun initVariable() {
    }

    override fun initView() {
        var arr = SparseArray<Int>()
        arr.put(R.layout.layout_channel_item,BR.channel);
        adapter = ChannelAdapter(mContext,list,arr,ClickEvt())
        recy_channel.layoutManager = LinearLayoutManager(mContext)
        recy_channel.adapter = adapter
    }

    inner class ClickEvt:IItemClick<ChannelData>{
        override fun itemClick(data: ChannelData) {
            mIntent.putExtra("channelId",data.id)
            mIntent.putExtra("channelName",data.name)
            setResult(102,mIntent)
            finish()
        }

    }
}