package com.example.heysrealprojcet.ui.channel.list.detail.approvedUser.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.heysrealprojcet.model.network.ChannelFollower
import com.example.heysrealprojcet.repository.ChannelRepository
import com.example.heysrealprojcet.repository.MyPageRepository
import com.example.heysrealprojcet.ui.base.BaseViewModel
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