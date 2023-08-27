package com.example.heysrealprojcet.ui.user.setting.delete

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.heysrealprojcet.databinding.SettingDeleteDecideFragmentBinding
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingWithdrawalCheckFragment : Fragment() {
   private lateinit var binding: SettingDeleteDecideFragmentBinding
   private val viewModel: SettingWithdrawalCheckViewModel by viewModels()
   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = SettingDeleteDecideFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   // TODO
   // 계정 탈퇴
   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.btnOk.setOnClickListener {

      }
   }

   private fun withdrawal(id: Int) {
      viewModel.withdrawal("Bearer ${UserPreference.accessToken}", id, "user").observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {

            }

            is NetworkResult.Loading -> {
               Log.w("withdrawal", "loading")
            }

            is NetworkResult.Error -> {
               Log.w("withdrawal", response.message.toString())
            }
         }
      }
   }
}