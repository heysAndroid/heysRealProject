package com.hey.heys.ui.channel.dialog.period

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hey.heys.util.ChannelPreference
import java.time.LocalDate
import java.time.YearMonth

class ChannelPeriodDialogViewModel : ViewModel() {
   var selectedList = arrayListOf<LocalDate>()

   private val _selectedDate: MutableLiveData<ArrayList<LocalDate>> = MutableLiveData()
   val selectedDate: LiveData<ArrayList<LocalDate>> = _selectedDate

   private val _isEnabled = MutableLiveData(false)
   val isEnabled: LiveData<Boolean> = _isEnabled

   private val _calendarPosition = MutableLiveData(YearMonth.now().month.value)
   val calendarPosition: LiveData<Int> = _calendarPosition

   fun updateSelectedDate(selectedDate: ArrayList<LocalDate>) {
      _selectedDate.value = selectedDate
      ChannelPreference.channelRecruitEndDay = selectedDate.last().toString()
   }

   fun onEnabled() {
      _isEnabled.value = true
   }

   fun unEnabled() {
      _isEnabled.value = false
   }

   fun plusPosition() {
      _calendarPosition.value = _calendarPosition.value!! + 1
   }

   fun minusPosition() {
      _calendarPosition.value = _calendarPosition.value!! - 1
   }

   fun setPosition(value: Int) {
      _calendarPosition.value = value
   }
}