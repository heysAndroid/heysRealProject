package com.example.heysrealprojcet.ui.user.setting

import androidx.lifecycle.asLiveData
import com.example.heysrealprojcet.repository.MyPageRepository
import com.example.heysrealprojcet.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val myPageRepository: MyPageRepository) : BaseViewModel() {
   fun deleteDeviceToken(token: String, deviceToken: String) =
      myPageRepository.deleteDeviceToken(token, deviceToken).asLiveData()
}