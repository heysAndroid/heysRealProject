package com.example.heysrealprojcet.ui.join.gender

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JoinGenderViewModel : ViewModel() {
   private val _isMale = MutableLiveData<Boolean>()
   val isMale: LiveData<Boolean> = _isMale

   fun onClickGender(v: View) {
      _isMale.value = v.tag == "male"
   }
}