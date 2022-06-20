package com.example.heysrealprojcet.ui.join.name

import android.view.View
import androidx.lifecycle.*
import com.example.heysrealprojcet.util.UserPreference
import kotlinx.coroutines.launch

class JoinNameViewModel : ViewModel() {
   val name = MutableLiveData<String>()

   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

   private val _isDescriptionVisible = MutableLiveData(View.INVISIBLE)
   val isDescriptionVisible: LiveData<Int> = _isDescriptionVisible

   init {
      viewModelScope.launch {
         name.asFlow().collect {
            UserPreference.name = it
            isCorrect()
            isVisible()
         }
      }
   }

   private fun isCorrect() {
      _isEnabled.value = !name.value.isNullOrBlank() && name.value?.length!! < 4
   }

   private fun isVisible() {
      if (name.value?.length!! > 3) {
         _isDescriptionVisible.value = View.VISIBLE
      } else {
         _isDescriptionVisible.value = View.INVISIBLE
      }
   }
}