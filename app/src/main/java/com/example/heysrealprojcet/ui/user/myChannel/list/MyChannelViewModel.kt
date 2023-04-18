package com.example.heysrealprojcet.ui.user.myChannel.list

import androidx.lifecycle.asLiveData
import com.example.heysrealprojcet.repository.ChannelRepository
import com.example.heysrealprojcet.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyChannelViewModel @Inject constructor(
   private val channelRepository: ChannelRepository) : BaseViewModel() {
   fun getMyChannelList(token: String) = channelRepository.getMyChannelList(token).asLiveData()
}