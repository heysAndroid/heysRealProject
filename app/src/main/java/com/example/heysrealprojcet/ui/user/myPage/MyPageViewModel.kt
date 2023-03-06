package com.example.heysrealprojcet.ui.user.myPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.model.network.response.MyPageResponse
import com.example.heysrealprojcet.repository.MyPageRepository
import com.example.heysrealprojcet.ui.base.BaseViewModel
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