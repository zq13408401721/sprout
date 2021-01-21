package com.sprout.vm

import com.shop.base.BaseViewModel
import com.sprout.net.InJection
import com.sprout.net.SproutRepository

class StartViewModel: BaseViewModel<SproutRepository>(InJection.repository) {
}