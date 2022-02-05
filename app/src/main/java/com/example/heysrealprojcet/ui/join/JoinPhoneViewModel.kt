package com.example.heysrealprojcet.ui.join

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class JoinPhoneViewModel : ViewModel() {
    val phone = MutableStateFlow("")
}