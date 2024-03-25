package com.hey.heys.ui.user.myChannel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.hey.heys.repository.MyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HeysChannelViewModel @Inject constructor(private val repository: MyPageRepository) : ViewModel() {

   private val _isSelected = MutableLiveData(true)
   val isSelected: LiveData<Boolean> = _isSelected

   fun setMyChannel() {
      _isSelected.value = true
   }

   fun setChannel() {
      _isSelected.value = false
   }

   fun getNotificationExist(token: String) = repository.getNotificationsExist(token).asLiveData()
}