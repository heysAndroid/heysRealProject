package com.example.heysrealprojcet.ui.user.myPage.edit

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.heysrealprojcet.model.network.MyPageEdit
import com.example.heysrealprojcet.repository.MyPageRepository
import com.example.heysrealprojcet.ui.base.BaseViewModel
import com.example.heysrealprojcet.util.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
   private val myPageRepository: MyPageRepository
) : BaseViewModel() {
   private val _introduceLetterCount = MutableLiveData(0)
   val introduceLetterCount = _introduceLetterCount

   private val _nameLetterCount = MutableLiveData(0)
   val nameLetterCount = _nameLetterCount

   private val _jobLetterCount = MutableLiveData(0)
   val jobLetterCount = _jobLetterCount

   private val _abilityLetterCount = MutableLiveData(0)
   val abilityLetterCount = _abilityLetterCount

   var introduce = MutableLiveData(UserPreference.introduce)
   var name = MutableLiveData(UserPreference.name)
   var mbti = MutableLiveData(UserPreference.mbti)
   var interestString = MutableLiveData(UserPreference.interests)
   var job = MutableLiveData(UserPreference.job)
   var capability = MutableLiveData(UserPreference.capability)
   var interestArray = mutableListOf<String>()

   val link1 = MutableLiveData("")
   val link2 = MutableLiveData("")
   val link3 = MutableLiveData("")
   val link4 = MutableLiveData("")
   val link5 = MutableLiveData("")

   fun editMyInfo(token: String, myPageEdit: MyPageEdit) = myPageRepository.editMyInfo(token, myPageEdit).asLiveData()

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