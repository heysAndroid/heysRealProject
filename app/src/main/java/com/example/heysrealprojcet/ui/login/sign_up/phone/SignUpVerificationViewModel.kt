package com.example.heysrealprojcet.ui.login.sign_up.phone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heysrealprojcet.util.UserPreference
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SignUpVerificationViewModel : ViewModel() {
   val phoneNumber = MutableLiveData<String>()
   val verificationNumber = MutableStateFlow("")

   private var _timeTextMinute = 3
   private var _timeTextSecond = 0
   private val _timeText = MutableLiveData<String>()
   private lateinit var job: Job

   val timeText: LiveData<String>
      get() = _timeText

   private val _is6Digit = MutableLiveData<Boolean>()
   val is6Digit: LiveData<Boolean> = _is6Digit

   var isResendPhoneAuth: Boolean = false
   var phoneAuthNumber: String = ""

   private val _requestPhoneAuth = MutableLiveData<Boolean>()
   val requestPhoneAuth: LiveData<Boolean> = _requestPhoneAuth

   private val _requestResendPhoneAuth = MutableLiveData<Boolean>()
   val requestResendPhoneAuth: LiveData<Boolean> = _requestResendPhoneAuth

   init {
      requestPhoneNumberAuth()
      timerStart()
      viewModelScope.launch {
         verificationNumber.collect {
            checkDigit()
         }
      }
   }

   private fun checkDigit() {
      _is6Digit.value = verificationNumber.value?.length == 6
   }

   fun timerStart() {
      // 초기화 확인 -> 종료
      if (::job.isInitialized) job.cancel()

      job = viewModelScope.launch {
         while (_timeTextMinute >= 0 && _timeTextSecond >= 0) {
            if (_timeTextMinute > 0 && _timeTextSecond == 0) {
               _timeTextMinute -= 1
               _timeTextSecond = 60
            }
            if ("$_timeTextSecond".length == 2) {
               _timeText.value = "${_timeTextMinute}분 ${_timeTextSecond}초"
            } else {
               _timeText.value = "${_timeTextMinute}분 0${_timeTextSecond}초"
            }
            _timeTextSecond -= 1
            delay(1000L)
         }
      }
   }

   private fun requestPhoneNumberAuth() {
      if (!isResendPhoneAuth) {
         _requestPhoneAuth.value = !UserPreference.phoneNumber.isNullOrBlank()
      } else {
         _requestResendPhoneAuth.value = !UserPreference.phoneNumber.isNullOrBlank()
      }
   }

   fun setResendEnabled() {
      _requestResendPhoneAuth.value = true
      timerStart()
   }
}