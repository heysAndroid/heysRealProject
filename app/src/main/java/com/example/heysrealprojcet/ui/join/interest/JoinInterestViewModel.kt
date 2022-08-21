package com.example.heysrealprojcet.ui.join.interest

import android.graphics.Typeface
import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.heysrealprojcet.model.User
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.model.network.response.LoginResponse
import com.example.heysrealprojcet.repository.SignupRepository
import com.example.heysrealprojcet.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Hilt 로 생성자에 repository 전달
@HiltViewModel
class JoinInterestViewModel @Inject constructor(
   private val signupRepository: SignupRepository) : BaseViewModel() {

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
   private val _response: MutableLiveData<NetworkResult<LoginResponse>> = MutableLiveData()
   val response: LiveData<NetworkResult<LoginResponse>> = _response

   /*
   * 시작하기 버튼
    */
   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

   init {
      viewModelScope.launch { total.collect { isSelected() } }
   }

   private fun isSelected() {
      _isEnabled.value = total.value > 0
   }

   fun onClickInterest(v: View) {
      val item = v.tag.toString()

      var button = v as Button

      if (total.value < totalMax) {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            total.value -= 1
            interestList.remove(item)
         } else {
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            total.value += 1
            interestList.add(item)
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            total.value -= 1
            interestList.remove(item)
         }
      }
      totalString.value = "${total.value}/$totalMax"
   }

   fun signup(user: User) = viewModelScope.launch {
      // repository 의 함수를 호출해서 _response 에 api 응답을 저장
      signupRepository.signup(user).collect { values ->
         _response.value = values
      }
   }
}