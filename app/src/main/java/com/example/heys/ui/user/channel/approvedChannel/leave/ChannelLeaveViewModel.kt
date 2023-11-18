package com.example.heys.ui.user.channel.approvedChannel.leave

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.heys.model.network.response.SimpleResponse
import com.example.heys.repository.ChannelRepository
import com.example.heys.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChannelLeaveViewModel @Inject constructor(private val channelRepository: ChannelRepository) : BaseViewModel() {
   val reason = MutableLiveData<String>()

   fun setReason(value: String) {
      reason.value = value
   }

   fun putExitChannel(token: String, channelId: Int, message: SimpleResponse) =
      channelRepository.putExitChannel(token, channelId, message).asLiveData()
}