package com.hey.heys.ui.user.setting.withdrawal

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hey.heys.R
import com.hey.heys.databinding.WithdrawalConfirmFragmentBinding
import com.hey.heys.model.network.NetworkResult
import com.hey.heys.model.network.WithdrawalReason
import com.hey.heys.ui.intro.IntroActivity
import com.hey.heys.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WithdrawalConfirmFragment : Fragment() {
   private lateinit var binding: WithdrawalConfirmFragmentBinding
   private val viewModel: WithdrawalConfirmViewModel by viewModels()
   private val args: WithdrawalConfirmFragmentArgs by navArgs()
   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = WithdrawalConfirmFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.btnOk.setOnClickListener { withdrawal(args.reason) }
      binding.btnCancle.setOnClickListener { goToMain() }
   }

   private fun withdrawal(reason: String) {
      val alert = AlertDialog.Builder(requireContext())
      val token = UserPreference.accessToken

      viewModel.withdrawal("Bearer $token", WithdrawalReason(reason)).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               UserPreference.init()
               val intent = Intent(requireContext(), IntroActivity::class.java)
               startActivity(intent)
               requireActivity().finish()
            }

            is NetworkResult.Loading -> {
               Log.w("withdrawal: ", "loading")
               alert.setTitle("탈퇴 지연").setMessage("탈퇴가 지연되고 있습니다.").create().show()
            }

            else -> {
               Log.w("withdrawal error: ", response.message.toString())
               alert.setTitle("탈퇴 실패").setMessage("탈퇴에 실패했습니다.").create().show()
            }
         }
      }
   }

   private fun goToMain() {
      findNavController().navigate(R.id.action_withdrawalConfirmFragment_to_mainFragment)
   }
}