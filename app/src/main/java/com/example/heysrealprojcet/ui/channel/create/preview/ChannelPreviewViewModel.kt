package com.example.heysrealprojcet.ui.channel.create.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.heysrealprojcet.util.ChannelPreference

class ChannelPreviewViewModel : ViewModel() {
   var channelPurpose = MutableLiveData(ChannelPreference.channelPurpose)
   var channelCapacity = MutableLiveData(ChannelPreference.channelCapacity.toString())
   var channelForm = MutableLiveData(ChannelPreference.channelForm)
   var channelRegion = MutableLiveData(ChannelPreference.channelRegion)
   var channelRecruitmentMethod = MutableLiveData(ChannelPreference.channelRecruitmentMethod)
}