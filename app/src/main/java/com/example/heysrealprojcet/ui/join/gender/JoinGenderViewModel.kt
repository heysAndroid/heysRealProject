package com.example.heysrealprojcet.ui.join.gender

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.heysrealprojcet.enums.Gender
import com.example.heysrealprojcet.util.UserPreference

class JoinGenderViewModel : ViewModel() {
   private val _isMale = MutableLiveData<Boolean>()
   val isMale: LiveData<Boolean> = _isMale

   fun onClickGender(v: View) {
      if (v.tag == "male") {
         _isMale.value = true
         UserPreference.gender = Gender.Male.gender
      } else {
         _isMale.value = false
         UserPreference.gender = Gender.Female.gender
      }
   }
}