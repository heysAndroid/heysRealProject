package com.example.heysrealprojcet.ui.user.myChannel.chat

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChattingReaderNoticeViewModel : ViewModel() {
   private val _titleLetterCount = MutableLiveData(0)
   val titleLetterCount: LiveData<Int> = _titleLetterCount

   private val _contentLetterCount = MutableLiveData(0)
   val contentLetterCount: LiveData<Int> = _contentLetterCount

   val titleWatcher = object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
         _titleLetterCount.value = s?.length
      }

      override fun afterTextChanged(s: Editable?) {

      }
   }

   val contentWatcher = object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
         _contentLetterCount.value = s?.length
      }

      override fun afterTextChanged(s: Editable?) {

      }

   }
}