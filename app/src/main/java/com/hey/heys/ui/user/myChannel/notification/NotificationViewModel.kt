package com.hey.heys.ui.user.myChannel.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.hey.heys.repository.MyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(private val myPageRepository: MyPageRepository) : ViewModel() {
   fun getNotification(token: String) = myPageRepository.getNotifications(token).asLiveData()
}