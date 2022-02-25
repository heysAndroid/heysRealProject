package com.example.heysrealprojcet.ui.join.phone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class JoinVerificationViewModel : ViewModel() {
   val phoneNumber = MutableLiveData<String>()
   val verificationNumber = MutableStateFlow("")

   private var _timeTextMinute: Int = 0
   private var _timeTextSecond: Int = 0
   private val _timeText = MutableLiveData<String>()
   private lateinit var a : Job

   val timeText: LiveData<String>
      get() = _timeText

   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

   init {
      viewModelScope.launch {
         verificationNumber.collect {
            isCorrectVerificationNumber()
         }
      }
   }

   private fun isCorrectVerificationNumber() {
      _isEnabled.value = verificationNumber.value?.length == 6
   }

   fun timerStart(){
      // 초기화 확인 -> 종료
      if(::a.isInitialized) a.cancel()

      _timeTextMinute = 3
      _timeTextSecond = 0

      a = viewModelScope.launch {
         while(_timeTextMinute >= 0 && _timeTextSecond >= 0) {
            if (_timeTextMinute > 0 && _timeTextSecond == 0){
               _timeTextMinute -= 1
               _timeTextSecond = 60
            }
            _timeTextSecond -= 1
            if("$_timeTextSecond".length == 2) {
               _timeText.value = "인증번호 입력 시간이 0${_timeTextMinute}분 ${_timeTextSecond}초 남았습니다"
            }
            else {
               _timeText.value = "인증번호 입력 시간이 0${_timeTextMinute}분 0${_timeTextSecond}초 남았습니다"
            }
            delay(1000L)
         }
      }
   }
}