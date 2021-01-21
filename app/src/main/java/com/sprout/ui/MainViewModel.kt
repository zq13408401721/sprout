package com.sprout.ui

import com.shop.base.BaseViewModel
import com.sprout.net.InJection
import com.sprout.net.SproutRepository

class MainViewModel:BaseViewModel<SproutRepository>(InJection.repository) {
}