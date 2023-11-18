package com.example.heys.ui.user.setting.withdrawal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.heys.repository.MyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WithdrawalConfirmViewModel @Inject constructor(
   private val myPageRepository: MyPageRepository
) : ViewModel() {
   fun withdrawal(token: String, reason: String) = myPageRepository.withdrawal(token, reason).asLiveData()
}