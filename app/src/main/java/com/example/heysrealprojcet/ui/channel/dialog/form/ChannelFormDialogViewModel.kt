package com.example.heysrealprojcet.ui.channel.dialog.form

import android.graphics.Typeface
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ChannelFormDialogViewModel : ViewModel() {
   var selectedView: View? = null
   var choiceRegion: View? = null
   var region: LinearLayout? = null

   private val formMax = 1
   private val regionMax = 1

   private var formTotal = MutableStateFlow(1)
   private var regionTotal = MutableStateFlow(1)

   var formText = "온, 오프라인"
   var regionText = "전국"

   fun onClickForm(v: View) {
      val button = v as Button
      formText = button.text.toString()
      selectedView?.isSelected = false

      if (formTotal.value < formMax) {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            formTotal.value -= 1
         } else {
            selectedView = v
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            formTotal.value += 1
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            formTotal.value -= 1
         } else {
            selectedView?.isSelected = false
            (selectedView as Button).setTypeface(null, Typeface.NORMAL)
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            selectedView = v
         }
      }

      if (formText.contains("온")) {
         region?.visibility = View.VISIBLE
      } else {
         region?.visibility = View.GONE
      }
   }

   fun onClickRegion(v: View) {
      val button = v as Button
      regionText = button.text.toString()
      choiceRegion?.isSelected = false

      if (formText.contains("온")) {
         if (regionTotal.value < regionMax) {
            if (v.isSelected) {
               choiceRegion = null
               v.isSelected = false
               button.setTypeface(null, Typeface.NORMAL)
               regionTotal.value -= 1
            } else {
               choiceRegion = v
               v.isSelected = true
               button.setTypeface(null, Typeface.BOLD)
               regionTotal.value += 1
            }
         } else {
            if (v.isSelected) {
               choiceRegion = null
               v.isSelected = false
               button.setTypeface(null, Typeface.NORMAL)
               regionTotal.value -= 1
            } else {
               choiceRegion?.isSelected = false
               (choiceRegion as Button).setTypeface(null, Typeface.NORMAL)
               v.isSelected = true
               button.setTypeface(null, Typeface.BOLD)
               choiceRegion = v
            }
         }
      }
   }
}