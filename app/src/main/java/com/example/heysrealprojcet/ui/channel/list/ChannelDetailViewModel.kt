package com.example.heysrealprojcet.ui.channel.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.model.network.response.ChannelDetailResponse
import com.example.heysrealprojcet.repository.ChannelRepository
import com.example.heysrealprojcet.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChannelDetailViewModel @Inject constructor(
   private val channelRepository: ChannelRepository) : BaseViewModel() {
   private val _response: MutableLiveData<NetworkResult<ChannelDetailResponse>> = MutableLiveData()
   val response: LiveData<NetworkResult<ChannelDetailResponse>> = _response

   fun getChannelDetail(token: String, id: Int) = viewModelScope.launch {
      channelRepository.getChannelDetail(token, id).collect { values ->
         _response.value = values
      }
   }
}