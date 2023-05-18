package com.example.heysrealprojcet.ui.main

import androidx.lifecycle.asLiveData
import com.example.heysrealprojcet.repository.MyPageRepository
import com.example.heysrealprojcet.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
   private val myPageRepository: MyPageRepository
) : BaseViewModel() {
   fun getMyInfo(token: String) = myPageRepository.getMyInfo(token).asLiveData()
}