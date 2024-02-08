package com.hey.heys.ui.channel.list.detail.waitingUser.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.hey.heys.model.network.ChannelFollower
import com.hey.heys.model.network.response.SimpleResponse
import com.hey.heys.repository.ChannelRepository
import com.hey.heys.repository.MyPageRepository
import com.hey.heys.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WaitingUserListViewModel @Inject constructor(private val channelRepository: ChannelRepository, private val userRepository: MyPageRepository) : BaseViewModel() {
   private val _waitingUserList = MutableLiveData<List<ChannelFollower>>()
   val waitingUserList: LiveData<List<ChannelFollower>> = _waitingUserList

   fun setWaitingUserList(value: List<ChannelFollower>) {
      _waitingUserList.value = value
   }

   fun getChannelFollower(token: String, channelId: Int, status: String) =
      channelRepository.getChannelFollower(token, channelId, status).asLiveData()

   fun getUsers(token: String, userId: Int) = userRepository.getUsers(token, userId).asLiveData()

   fun putRequestAllow(token: String, channelId: Int, followerId: Int, message: SimpleResponse = SimpleResponse(message = "환영합니다!")) =
      channelRepository.putRequestAllow(token, channelId, followerId, message).asLiveData()
}