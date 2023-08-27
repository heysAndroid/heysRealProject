package com.example.heysrealprojcet.ui.user.setting.delete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.heysrealprojcet.repository.MyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingWithdrawalCheckViewModel @Inject constructor(
   private val myPageRepository: MyPageRepository
) : ViewModel() {
   fun withdrawal(token: String, id: Int, role: String) = myPageRepository.withdrawal(token, id, role).asLiveData()
}