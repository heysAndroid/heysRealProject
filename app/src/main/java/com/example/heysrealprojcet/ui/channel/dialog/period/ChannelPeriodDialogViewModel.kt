package com.example.heysrealprojcet.ui.channel.dialog.period

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.heysrealprojcet.util.ChannelPreference
import java.time.LocalDate

class ChannelPeriodDialogViewModel : ViewModel() {
   var selectedList = arrayListOf<LocalDate>()

   private val _selectedDate: MutableLiveData<ArrayList<LocalDate>> = MutableLiveData()
   val selectedDate: LiveData<ArrayList<LocalDate>> = _selectedDate

   private val _isEnabled = MutableLiveData(false)
   val isEnabled: LiveData<Boolean> = _isEnabled

   fun updateSelectedDate(selectedDate: ArrayList<LocalDate>) {
      _selectedDate.value = selectedDate
      ChannelPreference.channelRecruitLastDay = selectedDate.last().toString()
   }

   fun onEnabled() {
      _isEnabled.value = true
   }

   fun unEnabled() {
      _isEnabled.value = false
   }
}