package com.example.heysrealprojcet.ui.main.content.contestActivity.filter

import android.graphics.Typeface
import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ContestActivityFilterViewModel : ViewModel() {
   private var choiceInterest = mutableListOf<View>()
   private var choiceActivity: View? = null
   private var choiceTime: View? = null
   private var choicePurpose: View? = null

   private val interestMax = 3
   private val activityMax = 1
   private val timeMax = 1
   private val purposeMax = 1

   private var interestTotal = MutableStateFlow(0)
   private var activityTotal = MutableStateFlow(0)
   private var timeTotal = MutableStateFlow(0)
   private var purposeTotal = MutableStateFlow(0)

   private val interestArray = mutableListOf<String>()
   private val activityArray = mutableListOf<String>()
   private val timeArray = mutableListOf<String>()
   private val purposeArray = mutableListOf<String>()

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
   }

   fun onClickActivity(v: View) {
      val item = v.tag.toString()

      val button = v as Button

      if (activityTotal.value < activityMax) {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            activityTotal.value -= 1
            activityArray.remove(item)
         } else {
            choiceActivity = v
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            activityTotal.value += 1
            activityArray.add(item)
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            activityTotal.value -= 1
            activityArray.remove(item)
         }
         else {
            choiceActivity?.isSelected = false
            (choiceActivity as Button).setTypeface(null, Typeface.NORMAL)
            activityArray.remove(activityArray[0])
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            choiceActivity = v
            activityArray.add(item)
         }
      }
   }

   fun onClickTime(v: View) {
      val item = v.tag.toString()

      var button = v as Button

      if (timeTotal.value < timeMax) {
         if (v.isSelected) {
            v.isSelected = false
            timeTotal.value -= 1
            button.setTypeface(null, Typeface.NORMAL)
            timeArray.remove(item)
         } else {
            choiceTime = v
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            timeTotal.value += 1
            timeArray.add(item)
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            timeTotal.value -= 1
            timeArray.remove(item)
         }
         else {
            choiceTime?.isSelected = false
            (choiceTime as Button).setTypeface(null, Typeface.NORMAL)
            timeArray.remove(timeArray[0])
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            choiceTime = v
            timeArray.add(item)
         }
      }
   }

   fun onClickPurpose(v: View) {
      val item = v.tag.toString()

      var button = v as Button

      if (purposeTotal.value < purposeMax) {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            purposeTotal.value -= 1
            purposeArray.remove(item)
         } else {
            choicePurpose = v
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            purposeTotal.value += 1
            purposeArray.add(item)
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            purposeTotal.value -= 1
            purposeArray.remove(item)
         }
         else {
            choicePurpose?.isSelected = false
            (choicePurpose as Button).setTypeface(null, Typeface.NORMAL)
            purposeArray.remove(purposeArray[0])
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            choicePurpose = v
            purposeArray.add(item)
         }
      }
   }

   fun onClickInit() {
      for (i in choiceInterest.indices) {
         choiceInterest[i].isSelected = false
         (choiceInterest[i] as Button).setTypeface(null, Typeface.NORMAL)
      }
      interestTotal.value = 0
      choiceInterest.clear()

      choiceActivity?.isSelected = false
      if (choiceActivity != null) {
         (choiceActivity as Button).setTypeface(null, Typeface.NORMAL)
      }
      activityTotal.value = 0
      activityArray.clear()

      choiceTime?.isSelected = false
      if (choiceTime != null) {
         (choiceTime as Button).setTypeface(null, Typeface.NORMAL)
      }
      timeTotal.value = 0
      timeArray.clear()

      choicePurpose?.isSelected = false
      if (choicePurpose != null) {
         (choicePurpose as Button).setTypeface(null, Typeface.NORMAL)
      }
      purposeTotal.value = 0
   }
}