package com.example.heysrealprojcet.ui.channel.dialog.recruitmentMethod

import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.heysrealprojcet.enums.ChannelRecruitmentMethod

class ChannelRecruitmentMethodDialogViewModel : ViewModel() {
   private val _selectedRecruitmentMethod = MutableLiveData<String>()
   val selectedRecruitmentMethod: LiveData<String> = _selectedRecruitmentMethod

   fun onClickMethod(v: View) {
      val button = v as Button
      val buttonText = button.text.toString()
      if (buttonText.contains(ChannelRecruitmentMethod.Decide.method)) {
         _selectedRecruitmentMethod.value = ChannelRecruitmentMethod.Decide.method
      } else {
         _selectedRecruitmentMethod.value = ChannelRecruitmentMethod.Approval.method
      }
   }
}