package com.example.heysrealprojcet.ui.join.name

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class JoinNameViewModel : ViewModel() {
    val name = MutableStateFlow("")
}