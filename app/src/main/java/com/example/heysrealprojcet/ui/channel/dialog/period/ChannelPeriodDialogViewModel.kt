package com.example.heysrealprojcet.ui.channel.dialog.period

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class ChannelPeriodDialogViewModel : ViewModel() {
   var selectedList = arrayListOf<LocalDate?>()

   private val _selectedDate: MutableLiveData<ArrayList<LocalDate?>> = MutableLiveData()
   val selectedDate: LiveData<ArrayList<LocalDate?>> = _selectedDate

   fun updateSelectedDate(selectedDate: ArrayList<LocalDate?>) {
      _selectedDate.value = selectedDate
   }
}