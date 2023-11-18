package com.example.heys.ui.user.channel.waitingChannel.cancel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.heys.model.network.response.SimpleResponse
import com.example.heys.repository.ChannelRepository
import com.example.heys.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChannelCancelViewModel @Inject constructor(private val channelRepository: ChannelRepository) : BaseViewModel() {
   val reason = MutableLiveData<String>()

   fun setReason(value: String) {
      reason.value = value
   }

   fun putMemberAbort(token: String, channelId: Int, message: SimpleResponse) =
      channelRepository.putMemberAbort(token, channelId, message).asLiveData()
}