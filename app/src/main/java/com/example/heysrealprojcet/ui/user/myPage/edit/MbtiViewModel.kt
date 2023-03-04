package com.example.heysrealprojcet.ui.user.myPage.edit

import android.graphics.Typeface
import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModel
import com.example.heysrealprojcet.util.UserPreference

class MbtiViewModel : ViewModel() {
   fun onClickMbti(v: View) {
      var button = v as Button
      button.isSelected = true
      button.setTypeface(null, Typeface.BOLD)
      UserPreference.mbti = button.text.toString()
   }
}