package com.hey.heys.ui.user.setting

import androidx.lifecycle.asLiveData
import com.hey.heys.repository.MyPageRepository
import com.hey.heys.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val myPageRepository: MyPageRepository) : BaseViewModel() {
   fun deleteDeviceToken(token: String, deviceToken: String) =
      myPageRepository.deleteDeviceToken(token, deviceToken).asLiveData()
}