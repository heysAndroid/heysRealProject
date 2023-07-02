package com.example.heysrealprojcet.ui.channel.list.detail.waitingUser.reject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.heysrealprojcet.model.network.response.SimpleResponse
import com.example.heysrealprojcet.repository.ChannelRepository
import com.example.heysrealprojcet.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChannelRejectViewModel @Inject constructor(private val channelRepository: ChannelRepository) : BaseViewModel() {
   val reason = MutableLiveData<String>()

   fun setReason(value: String) {
      reason.value = value
   }

   fun putRequestReject(token: String, channelId: Int, followerId: Int, message: SimpleResponse) =
      channelRepository.putRequestReject(token, channelId, followerId, message).asLiveData()
}