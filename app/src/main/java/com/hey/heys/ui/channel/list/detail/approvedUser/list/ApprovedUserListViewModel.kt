package com.hey.heys.ui.channel.list.detail.approvedUser.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.hey.heys.model.network.ChannelFollower
import com.hey.heys.repository.ChannelRepository
import com.hey.heys.repository.MyPageRepository
import com.hey.heys.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ApprovedUserListViewModel @Inject constructor(private val channelRepository: ChannelRepository, private val userRepository: MyPageRepository) : BaseViewModel() {
   private val _approvedUserList = MutableLiveData<List<ChannelFollower>>()
   val approvedUserList: LiveData<List<ChannelFollower>> = _approvedUserList

   fun setApprovedUser(value: List<ChannelFollower>) {
      _approvedUserList.value = value
   }

   fun getChannelFollower(token: String, channelId: Int, status: String) =
      channelRepository.getChannelFollower(token, channelId, status).asLiveData()

   fun getUsers(token: String, userId: Int) = userRepository.getUsers(token, userId).asLiveData()
}