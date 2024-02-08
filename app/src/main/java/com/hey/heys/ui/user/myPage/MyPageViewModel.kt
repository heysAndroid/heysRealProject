package com.hey.heys.ui.user.myPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hey.heys.model.network.NetworkResult
import com.hey.heys.model.network.response.MyPageResponse
import com.hey.heys.repository.MyPageRepository
import com.hey.heys.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
   private val myPageRepository: MyPageRepository
) : BaseViewModel() {
   private val _response: MutableLiveData<NetworkResult<MyPageResponse>> = MutableLiveData()
   val response: LiveData<NetworkResult<MyPageResponse>> = _response

   fun getMyInfo(token: String) = viewModelScope.launch {
      myPageRepository.getMyInfo(token).collect { values ->
         _response.value = values
      }
   }
}