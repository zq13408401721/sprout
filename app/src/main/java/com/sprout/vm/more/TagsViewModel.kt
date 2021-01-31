package com.sprout.vm.more

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shop.base.BaseViewModel
import com.sprout.model.BrandData
import com.sprout.model.GoodData
import com.sprout.net.InJection
import com.sprout.net.SproutRepository
import kotlinx.coroutines.launch

class TagsViewModel:BaseViewModel<SproutRepository>(InJection.repository) {


}