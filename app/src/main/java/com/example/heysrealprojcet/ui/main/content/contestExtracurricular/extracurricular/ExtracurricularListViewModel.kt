package com.example.heysrealprojcet.ui.main.content.contestExtracurricular.extracurricular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.model.network.response.PingResponse
import com.example.heysrealprojcet.repository.PingRepository
import com.example.heysrealprojcet.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExtracurricularListViewModel @Inject constructor(
   private val pingRepository: PingRepository) : BaseViewModel() {

   private val _response: MutableLiveData<NetworkResult<PingResponse>> = MutableLiveData()
   val response: LiveData<NetworkResult<PingResponse>> = _response

   fun ping() = viewModelScope.launch {
      pingRepository.ping().collect { values ->
         _response.value = values
      }
   }
}