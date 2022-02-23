package com.example.heysrealprojcet.ui.join.phone

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class JoinVerificationViewModel : ViewModel() {
   val phoneNumber = MutableLiveData<String>()
   val verificationNumber = MutableStateFlow("")
}