package com.example.heys.ui.user.myChannel.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.heys.model.network.MyChannel
import com.example.heys.repository.ChannelRepository
import com.example.heys.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyChannelViewModel @Inject constructor(
   private val channelRepository: ChannelRepository) : BaseViewModel() {
   private val _myChannelList = MutableLiveData<List<MyChannel>>()
   val myChannelList: LiveData<List<MyChannel>> = _myChannelList

   fun setMyChannelList(list: List<MyChannel>?) {
      _myChannelList.value = list ?: listOf()
   }

   fun getMyChannelList(token: String) = channelRepository.getMyChannelList(token).asLiveData()
}