package com.example.heys.ui.main

import androidx.lifecycle.asLiveData
import com.example.heys.repository.MyPageRepository
import com.example.heys.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
   private val myPageRepository: MyPageRepository
) : BaseViewModel() {
   fun getMyInfo(token: String) = myPageRepository.getMyInfo(token).asLiveData()

   fun getUsers(token: String, userId: Int) = myPageRepository.getUsers(token, userId).asLiveData()
}