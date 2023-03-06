package com.example.heysrealprojcet.ui.main.content.contestExtracurricular.contest.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContestExtracurricularDetailViewModel : ViewModel() {
   private var _showSnackBarEvent = MutableLiveData(false)
   val showSnackBarEvent: LiveData<Boolean> = _showSnackBarEvent

   private val _isSelected = MutableLiveData(false)
   val isSelected: LiveData<Boolean> = _isSelected

   fun onClickBookmark() {
      _isSelected.value = _isSelected.value != true
   }

   fun showSnackBar() {
      _showSnackBarEvent.value = true
   }
}