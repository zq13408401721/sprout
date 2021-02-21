package com.sprout.ui

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.shop.app.Constants
import com.shop.base.BaseActivity
import com.shop.utils.MyMmkv
import com.sprout.R
import com.sprout.app.Global
import com.sprout.databinding.ActivityMainBinding
import com.sprout.ui.discover.DiscoverFragment
import com.sprout.ui.home.HomeFragment
import com.sprout.ui.message.MessageFragment
import com.sprout.ui.mine.MineFragment
import com.sprout.ui.more.MoreEditorActivity

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(
    R.layout.activity_main,
    MainViewModel::class.java
),
    View.OnClickListener {

    lateinit var homeFragment:Fragment
    lateinit var discoverFragment:Fragment
    lateinit var messageFragment:Fragment
    lateinit var mineFragment:Fragment

    lateinit var transaction: FragmentTransaction

    override fun initData() {
        //模拟已经登录
        MyMmkv.setValue("uid","21244f6f-4aed-4914-b824-9deead555c79")
        MyMmkv.setValue(Constants.token_key,"sprout-token")
        MyMmkv.setValue(Constants.token,"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMjEyNDRmNmYtNGFlZC00OTE0LWI4MjQtOWRlZWFkNTU1Yzc5IiwicmFuZG9tIjoicHY1a255ejlwaCIsImlhdCI6MTYxMzg5MTY3NH0.9mkWj2fx5xKeKHFUKA6Cidpde_n3atMCJpv0byY5jCI")
    }

    override fun initVM() {

    }

    override fun initVariable() {

    }

    override fun initView() {

        homeFragment = HomeFragment.instance
        discoverFragment = DiscoverFragment.instance
        messageFragment = MessageFragment.instance
        mineFragment = MineFragment.instance
        //初始化第一个fragment
        transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragments,homeFragment)
        transaction.commit()

        mDataBinding.layoutHome.setOnClickListener(this)
        mDataBinding.layoutDiscover.setOnClickListener(this)
        mDataBinding.layoutMore.setOnClickListener(this)
        mDataBinding.layoutMessage.setOnClickListener(this)
        mDataBinding.layoutMine.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        resetImg()
        when(v!!.id){
            R.id.layout_home -> {
                mDataBinding.imgHome.setImageResource(R.mipmap.main_nav_home_hl)
                transaction.replace(R.id.fragments,homeFragment)
            }
            R.id.layout_discover -> {
                mDataBinding.imgDiscover.setImageResource(R.mipmap.main_nav_discover_hl)
                transaction.replace(R.id.fragments,discoverFragment)
            }
            R.id.layout_more -> {
                //判断用户是否登录
                val uid = MyMmkv.getString("uid")
                if(uid.isNullOrEmpty()){
                    //登录
                }else{
                    var intent = Intent(mContext,MoreEditorActivity::class.java)
                    startActivity(intent)
                }
            }
            R.id.layout_message -> {
                mDataBinding.imgMessage.setImageResource(R.mipmap.main_nav_message_hl)
                transaction.replace(R.id.fragments,messageFragment)
            }
            R.id.layout_mine -> {
                mDataBinding.imgMine.setImageResource(R.mipmap.main_nav_mine_hl)
                transaction.replace(R.id.fragments,mineFragment)
            }
        }
    }

    private fun resetImg(){
        mDataBinding.imgHome.setImageResource(R.mipmap.main_nav_home_normal)
        mDataBinding.imgDiscover.setImageResource(R.mipmap.main_nav_discover_normal)
        mDataBinding.imgMessage.setImageResource(R.mipmap.main_nav_message_normal)
        mDataBinding.imgMine.setImageResource(R.mipmap.main_nav_mine_normal)
    }
}