package com.sprout.ui.message

import androidx.fragment.app.Fragment
import com.sprout.ui.home.HomeFragment

class MessageFragment:Fragment() {
  companion object{
    val instance: MessageFragment by lazy { MessageFragment() }
  }
}