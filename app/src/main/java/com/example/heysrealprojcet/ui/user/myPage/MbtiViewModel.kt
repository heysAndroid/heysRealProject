package com.example.heysrealprojcet.ui.user.myPage

import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MbtiViewModel : ViewModel() {
   var mbti = MutableStateFlow("")

   fun setMbti(value: String) {
      mbti.value = value
   }

   fun onClickMbti(v: View) {
      var button = v as Button
      mbti.value = button.text.toString()
   }
}