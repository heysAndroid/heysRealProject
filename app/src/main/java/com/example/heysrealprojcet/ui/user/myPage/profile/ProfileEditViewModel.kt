package com.example.heysrealprojcet.ui.user.myPage.profile

import android.graphics.Typeface
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ProfileEditViewModel : ViewModel() {
   private val _introduceLetterCount = MutableLiveData(0)
   val introduceLetterCount = _introduceLetterCount

   private val _nameLetterCount = MutableLiveData(0)
   val nameLetterCount = _nameLetterCount

   private val _jobLetterCount = MutableLiveData(0)
   val jobLetterCount = _jobLetterCount

   private val _abilityLetterCount = MutableLiveData(0)
   val abilityLetterCount = _abilityLetterCount

   private var choiceInterest = mutableListOf<View>()
   private val interestMax = 3
   private var interestTotal = MutableStateFlow(0)
   val interestString = MutableStateFlow("0")
   private val interestArray = mutableListOf<String>()

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

   fun onClickInterest(v: View) {
      val item = v.tag.toString()

      var button = v as Button

      if (interestTotal.value < interestMax) {
         if (v.isSelected) {
            choiceInterest.remove(v)
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            interestTotal.value -= 1
            interestArray.remove(item)
         } else {
            choiceInterest.add(v)
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            interestTotal.value += 1
            interestArray.add(item)
         }
      } else {
         if (v.isSelected) {
            choiceInterest.remove(v)
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            interestTotal.value -= 1
            interestArray.remove(item)
         }
      }
      interestString.value = "${interestTotal.value}"
   }
}