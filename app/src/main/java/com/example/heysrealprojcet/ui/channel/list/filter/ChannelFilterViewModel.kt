package com.example.heysrealprojcet.ui.channel.list.filter

import android.graphics.Typeface
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.heysrealprojcet.Event
import java.time.LocalDate
import java.time.YearMonth

class ChannelFilterViewModel : ViewModel() {
   private val interestMax = 3

   private val _selectedForm = MutableLiveData<String?>()
   val selectedForm: LiveData<String?> = _selectedForm

   private val _selectedRegion = MutableLiveData<String?>()
   val selectedRegion: LiveData<String?> = _selectedRegion

   private val _selectedPurpose = MutableLiveData<ArrayList<String>>(arrayListOf())
   val selectedPurpose: LiveData<ArrayList<String>> = _selectedPurpose

   private val _selectedInterest = MutableLiveData<ArrayList<String>>(arrayListOf())
   val selectedInterest: LiveData<ArrayList<String>> = _selectedInterest

   private val _isCalendarInit = MutableLiveData<Event<Boolean>>()
   val isCalendarInit: LiveData<Event<Boolean>> = _isCalendarInit

   // 달력
   var selectedDate: LocalDate? = null

   private val _calendarPosition = MutableLiveData(YearMonth.now().month.value)
   val calendarPosition: LiveData<Int> = _calendarPosition

   private val _calendarDate = MutableLiveData<LocalDate>()
   val calendarDate: LiveData<LocalDate> = _calendarDate

   fun plusPosition() {
      _calendarPosition.value = _calendarPosition.value!! + 1
   }

   fun minusPosition() {
      _calendarPosition.value = _calendarPosition.value!! - 1
   }

   fun setPosition(value: Int) {
      _calendarPosition.value = value
   }

   fun setCalendarDate() {
      _calendarDate.value = selectedDate
   }

   fun onClickInterest(v: View) {
      val button = v as Button
      val text = "${button.text}"

      if (selectedInterest.value?.size!! < interestMax) {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            _selectedInterest.value?.remove(text)
         } else {
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            _selectedInterest.value?.add(text)
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            _selectedInterest.value?.remove(text)
         }
      }

      _selectedInterest.apply { postValue(value) }
   }

   fun onClickRegion(v: View) {
      val button = v as Button
      _selectedRegion.value = "${button.text}"
   }

   fun setDefaultRegion() {
      _selectedRegion.value = "전국"
   }

   fun onClickForm(v: View) {
      val button = v as Button
      _selectedForm.value = "${button.tag}"
   }

   fun onClickPurpose(v: View) {
      val button = v as Button
      val text = "${button.text}"

      if (selectedPurpose.value?.contains(text) == true) {
         _selectedPurpose.value?.remove(text)
         _selectedPurpose.apply { postValue(value) }
      } else {
         _selectedPurpose.value?.add(text)
         _selectedPurpose.apply { postValue(value) }
      }
   }

   fun init() {
      _selectedInterest.value?.clear()

      selectedDate = null
      _calendarDate.value = selectedDate
      _isCalendarInit.value = Event(true)

      _selectedForm.value = null
      _selectedRegion.value = null
      _selectedPurpose.value?.clear()
   }
}