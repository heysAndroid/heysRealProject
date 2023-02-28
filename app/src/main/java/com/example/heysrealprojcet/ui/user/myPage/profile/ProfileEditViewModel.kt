package com.example.heysrealprojcet.ui.user.myPage.profile

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileEditViewModel : ViewModel() {
   private val _introduceLetterCount = MutableLiveData(0)
   val introduceLetterCount = _introduceLetterCount

   private val _nameLetterCount = MutableLiveData(0)
   val nameLetterCount = _nameLetterCount

   private val _jobLetterCount = MutableLiveData(0)
   val jobLetterCount = _jobLetterCount

   private val _abilityLetterCount = MutableLiveData(0)
   val abilityLetterCount = _abilityLetterCount

   var radioChecked = MutableLiveData<Int>()

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

   val jobWatcher = object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
         _jobLetterCount.value = s?.length
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