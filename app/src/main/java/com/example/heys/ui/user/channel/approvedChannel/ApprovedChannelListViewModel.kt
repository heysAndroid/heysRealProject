package com.example.heys.ui.user.channel.approvedChannel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.heys.model.network.MyChannel
import com.example.heys.repository.ChannelRepository
import com.example.heys.repository.MyPageRepository
import com.example.heys.ui.base.BaseViewModel
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