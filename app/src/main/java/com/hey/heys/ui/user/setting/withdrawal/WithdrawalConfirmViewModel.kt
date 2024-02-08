package com.hey.heys.ui.user.setting.withdrawal

import androidx.lifecycle.asLiveData
import com.hey.heys.model.network.WithdrawalReason
import com.hey.heys.repository.MyPageRepository
import com.hey.heys.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WithdrawalConfirmViewModel @Inject constructor(
   private val myPageRepository: MyPageRepository
) : BaseViewModel() {
   fun withdrawal(token: String, reason: WithdrawalReason) = myPageRepository.withdrawal(token, reason).asLiveData()
}