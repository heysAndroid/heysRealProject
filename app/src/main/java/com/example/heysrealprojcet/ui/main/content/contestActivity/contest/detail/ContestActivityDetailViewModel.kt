package com.example.heysrealprojcet.ui.main.content.contestActivity.contest.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContestActivityDetailViewModel : ViewModel() {

   private var _bookmark = MutableLiveData<Boolean>()
   val bookmark: LiveData<Boolean> = _bookmark

   fun clickBookmark() {
      _bookmark.value = _bookmark.value != true
   }
}