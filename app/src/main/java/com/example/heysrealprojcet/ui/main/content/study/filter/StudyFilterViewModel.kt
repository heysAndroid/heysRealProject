package com.example.heysrealprojcet.ui.main.content.study.filter

import android.view.View
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class StudyFilterViewModel : ViewModel() {
   private var choiceRegion: View? = null

   private val interestMax = 3
   private val activityMax = 1
   private val regionMax = 1
   private val timeMax = 1

   private var interestTotal = MutableStateFlow(0)
   private var activityTotal = MutableStateFlow(0)
   private var regionTotal = MutableStateFlow(0)
   private var timeTotal = MutableStateFlow(0)


   private val interestArray = mutableListOf<String>()
   private val activityArray = mutableListOf<String>()
   private val regionArray = mutableListOf<String>()
   private val timeArray = mutableListOf<String>()


   fun onClickInterest(v: View) {
      val item = v.tag.toString()

      if (interestTotal.value < interestMax) {
         if (v.isSelected) {
            v.isSelected = false
            interestTotal.value -= 1
            interestArray.remove(item)
         } else {
            v.isSelected = true
            interestTotal.value += 1
            interestArray.add(item)
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            interestTotal.value -= 1
            interestArray.remove(item)
         }
      }
   }

   fun onClickActivity(v: View) {
      val item = v.tag.toString()

      if (activityTotal.value < activityMax) {
         if (v.isSelected) {
            v.isSelected = false
            activityTotal.value -= 1
            activityArray.remove(item)
         } else {
            v.isSelected = true
            activityTotal.value += 1
            activityArray.add(item)
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            activityTotal.value -= 1
            activityArray.remove(item)

            // 대면·비대면, 대면 클릭 해제시 지역 클릭 해제
            if (choiceRegion != null) {
               choiceRegion!!.isSelected = false
               regionTotal.value -= 1
               regionArray.remove(regionArray[0])
               choiceRegion = null
            }
         }
      }
   }

   fun onClickRegion(v: View) {
      val item = v.tag.toString()

      try {
         if (activityArray[0].slice(0..5) == "contact") {
            if (regionTotal.value < regionMax) {
               if (v.isSelected) {
                  choiceRegion = null
                  v.isSelected = false
                  regionTotal.value -= 1
                  regionArray.remove(item)
               } else {
                  choiceRegion = v
                  v.isSelected = true
                  regionTotal.value += 1
                  regionArray.add(item)
               }
            } else {
               if (v.isSelected) {
                  choiceRegion = null
                  v.isSelected = false
                  regionTotal.value -= 1
                  regionArray.remove(item)
               }
            }
         }
      } catch (e: Exception) {
         //Toast.makeText(this, "비대면의 경우 활동 지역을 선택할 수 없습니다.", Toast.LENGTH_SHORT).show()
      }
   }

   fun onClickPurpose(v: View) {
      val item = v.tag.toString()

      if (timeTotal.value < timeMax) {
         if (v.isSelected) {
            v.isSelected = false
            timeTotal.value -= 1
            timeArray.remove(item)
         } else {
            v.isSelected = true
            timeTotal.value += 1
            timeArray.add(item)
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            timeTotal.value -= 1
            timeArray.remove(item)
         }
      }
   }
}