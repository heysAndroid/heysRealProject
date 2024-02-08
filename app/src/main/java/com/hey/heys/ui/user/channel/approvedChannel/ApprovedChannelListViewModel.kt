package com.hey.heys.ui.user.channel.approvedChannel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.hey.heys.model.network.MyChannel
import com.hey.heys.repository.ChannelRepository
import com.hey.heys.repository.MyPageRepository
import com.hey.heys.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ApprovedChannelListViewModel @Inject constructor(private val channelRepository: ChannelRepository, private val userRepository: MyPageRepository) : BaseViewModel() {
   private val _approvedChannelList = MutableLiveData<List<MyChannel>>()
   val approvedChannelList: LiveData<List<MyChannel>> = _approvedChannelList

   fun setApprovedChannel(value: List<MyChannel>) {
      _approvedChannelList.value = value
   }

   fun getMyChannel(token: String, status: String) =
      channelRepository.getMyChannelByStatus(token, status).asLiveData()
}