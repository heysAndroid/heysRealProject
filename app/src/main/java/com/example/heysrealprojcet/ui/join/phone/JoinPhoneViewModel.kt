package com.example.heysrealprojcet.ui.join.phone

import android.util.Log
import androidx.lifecycle.*
import com.example.heysrealprojcet.repository.SettingRepository
import com.example.heysrealprojcet.util.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoinPhoneViewModel @Inject constructor(
    private val settingRepository: SettingRepository) : ViewModel() {
    val phoneNumber = MutableLiveData("")

    private val _isEnabled = MutableLiveData<Boolean>()
    val isEnabled: LiveData<Boolean> = _isEnabled

    init {
        viewModelScope.launch { getCategory() }
        viewModelScope.launch {
            phoneNumber.asFlow().collect {
                if (phoneNumber.value?.contains('-') == true) {
                    UserPreference.phoneNumber = phoneNumber.value?.replace("-", "").toString()
                }
                isElevenDigit()
            }
        }
    }

    private fun isElevenDigit() {
        _isEnabled.value = phoneNumber.value?.length == 13
    }

    // api 호출 테스트용도
    fun getCategory() {
        CoroutineScope(Dispatchers.IO).launch {
            settingRepository.getCategory()?.let { response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.w("====== test ======", it.toString())
                    }
                }
            }
        }
    }
}