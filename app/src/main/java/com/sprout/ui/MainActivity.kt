package com.sprout.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bytedance.sdk.openadsdk.AdSlot
import com.bytedance.sdk.openadsdk.TTAdConstant
import com.bytedance.sdk.openadsdk.TTAdNative.RewardVideoAdListener
import com.bytedance.sdk.openadsdk.TTAdSdk
import com.bytedance.sdk.openadsdk.TTRewardVideoAd
import com.shop.app.Constants
import com.shop.base.BaseActivity
import com.shop.utils.MyMmkv
import com.sprout.R
import com.sprout.databinding.ActivityMainBinding
import com.sprout.ui.discover.DiscoverFragment
import com.sprout.ui.home.HomeFragment
import com.sprout.ui.login.LoginActivity
import com.sprout.ui.message.MessageFragment
import com.sprout.ui.mine.MineFragment
import com.sprout.ui.more.MoreEditorActivity
import org.jetbrains.anko.act


class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(
        R.layout.activity_main,
        MainViewModel::class.java
),
    View.OnClickListener {

    final val CODE_LOGIN = 100001

    var homeFragment:Fragment?=null
    var discoverFragment:Fragment?=null
    var messageFragment:Fragment?=null
    var mineFragment:Fragment?=null
    var activity:Activity? = null

    override fun initData() {
        //模拟已经登录
        MyMmkv.setValue("uid", "21244f6f-4aed-4914-b824-9deead555c79")
        MyMmkv.setValue(Constants.token_key, "sprout-token")
        MyMmkv.setValue(Constants.token, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiNjE4NjhiMTktZWY5Zi00YjFmLWFiZDItOWI0MzI0ZTc5MWU5IiwiaWF0IjoxNjE1MTY1NzMzfQ.dcsSo5vf4qk_wbnK5-Y5MGmgBhXlbCdggCWbOEShuvo")

        var uid = MyMmkv.getString("uid")
        if(uid == null || uid.isNullOrEmpty()){
            var intent = Intent(this,LoginActivity::class.java)
            startActivityForResult(intent,CODE_LOGIN)
        }
    }

    override fun initVM() {

    }

    override fun initVariable() {

    }

    override fun initView() {
        activity = this
        var window = this.window
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
        //隐藏actionbar
        if(actionBar != null){
            actionBar!!.hide()
        }
        /*homeFragment = HomeFragment.instance
        discoverFragment = DiscoverFragment.instance
        messageFragment = MessageFragment.instance
        mineFragment = MineFragment.instance*/
        //初始化第一个fragment
        var transaction = supportFragmentManager.beginTransaction()
        /*hideFragments(transaction)
        transaction.show(homeFragment)
        transaction.commit()*/

        initHomeFragment(transaction)

        mDataBinding.layoutHome.setOnClickListener(this)
        mDataBinding.layoutDiscover.setOnClickListener(this)
        mDataBinding.layoutMore.setOnClickListener(this)
        mDataBinding.layoutMessage.setOnClickListener(this)
        mDataBinding.layoutMine.setOnClickListener(this)
        //initAdvert()
    }


    fun initAdvert(){
        val mTTAdNative = TTAdSdk.getAdManager().createAdNative(this)
        var adSlot = AdSlot.Builder()
                .setCodeId("945886573")
                .setRewardName("金币") //奖励的名称 选填
                .setRewardAmount(3)  //奖励的数量 选填
                //模板广告需要设置期望个性化模板广告的大小,单位dp,激励视频场景，只要设置的值大于0即可
                //.setExpressViewAcceptedSize(100f, 100f)
                .setUserID("tag123")//tag_id
                .setMediaExtra("media_extra") //附加参数
                .setOrientation(TTAdConstant.HORIZONTAL) //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                .build()
        mTTAdNative.loadRewardVideoAd(adSlot, object : RewardVideoAdListener {
            //请求广告失败
            override fun onError(code: Int, message: String) {
                print("onError")
            }

            //视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞。
            override fun onRewardVideoCached() {
                //开发者做一个标识
               print("onRewardVideoCached")
            }

            //视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。
            override fun onRewardVideoAdLoad(ad: TTRewardVideoAd) {
                //开发者做一个标识
                print("onRewardVideoAdLoad")
                ad.showRewardVideoAd(activity)
            }
        })
    }

    override fun onClick(v: View?) {
        var transaction = supportFragmentManager.beginTransaction()
        resetImg()
        resetTabText()
        when(v!!.id){
            R.id.layout_home -> {
                mDataBinding.imgHome.setImageResource(R.mipmap.main_nav_home_hl)
                initHomeFragment(transaction)
            }
            R.id.layout_discover -> {
                mDataBinding.imgDiscover.setImageResource(R.mipmap.main_nav_discover_hl)
                initDiscoverFragment(transaction)
            }
            R.id.layout_more -> {
                //判断用户是否登录
                val uid = MyMmkv.getString("uid")
                if (uid.isNullOrEmpty()) {
                    //登录
                } else {
                    var intent = Intent(mContext, MoreEditorActivity::class.java)
                    startActivity(intent)
                }
            }
            R.id.layout_message -> {
                mDataBinding.imgMessage.setImageResource(R.mipmap.main_nav_message_hl)
                initMessageFragment(transaction)
            }
            R.id.layout_mine -> {
                mDataBinding.imgMine.setImageResource(R.mipmap.main_nav_mine_hl)
                initMineFragment(transaction)
            }
        }
    }

    /**
     * 初始化主页
     */
    private fun initHomeFragment(transaction: FragmentTransaction){
        if(homeFragment == null){
            homeFragment = HomeFragment.instance
            transaction.add(R.id.fragments, homeFragment!!)
        }
        hideFragments(transaction)
        transaction.show(homeFragment!!)
        transaction.commit()
        mDataBinding.txtHome.setTextColor(Color.parseColor("#658E36"))
    }

    /**
     * 初始化发现
     */
    private fun initDiscoverFragment(transaction: FragmentTransaction){
        if(discoverFragment == null){
            discoverFragment = DiscoverFragment.instance
            transaction.add(R.id.fragments, discoverFragment!!)
        }
        hideFragments(transaction)
        transaction.show(discoverFragment!!)
        transaction.commit()
        mDataBinding.txtDiscover.setTextColor(Color.parseColor("#658E36"))
    }

    /**
     *初始化消息页面
     */
    private fun initMessageFragment(transaction: FragmentTransaction){
        if(messageFragment == null){
            messageFragment = MessageFragment.instance
            transaction.add(R.id.fragments, messageFragment!!)
        }
        hideFragments(transaction)
        transaction.show(messageFragment!!)
        transaction.commit()
        mDataBinding.txtMessage.setTextColor(Color.parseColor("#658E36"))
    }

    /**
     * 初始化我的页面
     */
    private fun initMineFragment(transaction: FragmentTransaction){
        if(mineFragment == null){
            mineFragment = MineFragment.instance
            transaction.add(R.id.fragments, mineFragment!!)
        }
        hideFragments(transaction)
        transaction.show(mineFragment!!)
        transaction.commit()
        mDataBinding.txtMine.setTextColor(Color.parseColor("#658E36"))
    }

    /**
     * 隐藏fragment
     */
    private fun hideFragments(trans: FragmentTransaction){
        if(homeFragment != null){
            trans.hide(homeFragment!!)
        }
        if(discoverFragment != null){
            trans.hide(discoverFragment!!)
        }
        if(messageFragment != null){
            trans.hide(messageFragment!!)
        }
        if(mineFragment != null){
            trans.hide(mineFragment!!)
        }
    }

    /**
     * 重置文本样式
     */
    private fun resetTabText(){
        mDataBinding.txtHome.setTextColor(Color.parseColor("#000000"))
        mDataBinding.txtDiscover.setTextColor(Color.parseColor("#000000"))
        mDataBinding.txtMessage.setTextColor(Color.parseColor("#000000"))
        mDataBinding.txtMine.setTextColor(Color.parseColor("#000000"))
    }

    private fun resetImg(){
        mDataBinding.imgHome.setImageResource(R.mipmap.main_nav_home_normal)
        mDataBinding.imgDiscover.setImageResource(R.mipmap.main_nav_discover_normal)
        mDataBinding.imgMessage.setImageResource(R.mipmap.main_nav_message_normal)
        mDataBinding.imgMine.setImageResource(R.mipmap.main_nav_mine_normal)
    }
}