package com.example.heysrealprojcet.ui.join.interest

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heysrealprojcet.model.User
import com.example.heysrealprojcet.repository.SignupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoinInterestViewModel @Inject constructor(
   private val signupRepository: SignupRepository) : ViewModel() {

   /*
   * 관심 분야
    */
   private val totalMax = 3
   private var total = MutableStateFlow(0)
   val totalString = MutableStateFlow("${total.value}/3")
   private val interestList = mutableListOf<String>()

   /*
   * 네트워크 호출
    */
   private val _isSuccess = MutableLiveData<Boolean>()
   val isSuccess: LiveData<Boolean> = _isSuccess

   /*
   * 시작하기 버튼
    */
   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

   val accessToken = MutableLiveData<String>()

   init {
      viewModelScope.launch {
         total.collect {
            isSelected()
         }
      }
   }

   private fun isSelected() {
      _isEnabled.value = total.value > 0
   }

   fun onClickInterest(v: View) {
      val item = v.tag.toString()

      if (total.value < totalMax) {
         if (v.isSelected) {
            v.isSelected = false
            total.value -= 1
            interestList.remove(item)
         } else {
            v.isSelected = true
            total.value += 1
            interestList.add(item)
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            total.value -= 1
            interestList.remove(item)
         }
      }
      totalString.value = "${total.value}/$totalMax"
   }

   fun signup(user: User) {
      CoroutineScope(Dispatchers.IO).launch {
         signupRepository.signup(user)?.let { response ->
            if (response.isSuccessful) {
               response.body()?.let { body ->
                  _isSuccess.postValue(true)
                  accessToken.postValue(body.accessToken)
                  Log.w("====== sign up ======", body.toString())
               }
            } else {
               _isSuccess.postValue(false)
               Log.e("======== ERROR ========", response.message().toString())
            }
         }
      }
   }
}