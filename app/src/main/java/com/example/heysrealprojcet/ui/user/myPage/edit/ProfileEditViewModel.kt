package com.example.heysrealprojcet.ui.user.myPage.edit

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.heysrealprojcet.util.UserPreference

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
   val link1 = MutableLiveData<String>()

   var introduce = MutableLiveData(UserPreference.introduce)
   var name = MutableLiveData(UserPreference.name)
   var mbti = MutableLiveData(UserPreference.mbti)
   var interestString = MutableLiveData(UserPreference.interests)
   var job = MutableLiveData(UserPreference.job)
   var skill = MutableLiveData(UserPreference.skill)
   var interestArray = mutableListOf<String>()

   init {
      interestString.value = interestString.value?.replace("\"", "")?.replace("[", "")?.replace("]", "")
      interestString.value?.split(",")?.toMutableList()?.let { interestArray = it }
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