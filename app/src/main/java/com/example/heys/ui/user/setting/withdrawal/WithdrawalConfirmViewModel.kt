package com.example.heys.ui.user.setting.withdrawal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.heys.Event
import com.example.heys.model.network.WithdrawalReason
import com.example.heys.repository.MyPageRepository
import com.example.heys.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class WithdrawalConfirmViewModel @Inject constructor(
   private val myPageRepository: MyPageRepository
) : BaseViewModel() {
   private val _response: MutableLiveData<Event<Response<Void>>> = MutableLiveData()
   val response: LiveData<Event<Response<Void>>> = _response

   var job: Job? = null

   fun withdrawal(token: String, reason: WithdrawalReason) = viewModelScope.launch {
      job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
         _response.postValue(Event(myPageRepository.withdrawal(token, reason)))
      }
   }
}