package com.example.heys.ui.user.myPage.edit

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.heys.model.network.MyPageEdit
import com.example.heys.model.network.NetworkResult
import com.example.heys.model.network.response.MyPageResponse
import com.example.heys.repository.MyPageRepository
import com.example.heys.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
   private val myPageRepository: MyPageRepository
) : BaseViewModel() {
   private val _introduceLetterCount = MutableLiveData(0)
   val introduceLetterCount = _introduceLetterCount

   private val _nameLetterCount = MutableLiveData(0)
   val nameLetterCount = _nameLetterCount

   private val _abilityLetterCount = MutableLiveData(0)
   val abilityLetterCount = _abilityLetterCount

   private val _response: MutableLiveData<NetworkResult<MyPageResponse>> = MutableLiveData()
   val response: LiveData<NetworkResult<MyPageResponse>> = _response

   var introduce = MutableLiveData("")
   var name = MutableLiveData("")
   var mbti = MutableLiveData("")
   var job = MutableLiveData("")
   var capability = MutableLiveData("")
   var interestArray = mutableListOf<String>()

   val link1 = MutableLiveData("")
   val link2 = MutableLiveData("")
   val link3 = MutableLiveData("")
   val link4 = MutableLiveData("")
   val link5 = MutableLiveData("")

   fun editMyInfo(token: String, myPageEdit: MyPageEdit) = myPageRepository.editMyInfo(token, myPageEdit).asLiveData()

   fun getMyInfo(token: String) = viewModelScope.launch {
      myPageRepository.getMyInfo(token).collect { values ->
         _response.value = values
      }
   }

   val introduceWatcher = object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
         _introduceLetterCount.value = s?.length
      }

      override fun afterTextChanged(s: Editable?) {
      }
   }

   val nameWatcher = object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
         _nameLetterCount.value = s?.length
      }

      override fun afterTextChanged(s: Editable?) {
      }
   }

   val abilityWatcher = object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
         _abilityLetterCount.value = s?.length
      }

      override fun afterTextChanged(s: Editable?) {
      }
   }
}