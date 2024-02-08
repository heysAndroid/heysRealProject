package com.hey.heys.ui.user.channel.waitingChannel.cancel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.hey.heys.model.network.response.SimpleResponse
import com.hey.heys.repository.ChannelRepository
import com.hey.heys.ui.base.BaseViewModel
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