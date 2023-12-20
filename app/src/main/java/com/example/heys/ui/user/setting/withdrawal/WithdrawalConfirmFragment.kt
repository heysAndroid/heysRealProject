package com.example.heys.ui.user.setting.withdrawal

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
import com.example.heys.EventObserver
import com.example.heys.R
import com.example.heys.databinding.WithdrawalConfirmFragmentBinding
import com.example.heys.model.network.WithdrawalReason
import com.example.heys.ui.intro.IntroActivity
import com.example.heys.util.UserPreference
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
      viewModel.withdrawal("Bearer ${UserPreference.accessToken}", WithdrawalReason(reason))
      viewModel.response.observe(viewLifecycleOwner, EventObserver { response ->
         val alert = AlertDialog.Builder(requireContext())
         when (response.isSuccessful) {
            true -> {
               val intent = Intent(requireContext(), IntroActivity::class.java)
               startActivity(intent)
               requireActivity().finish()
            }

            false -> {
               alert.setTitle("탈퇴 실패").setMessage("탈퇴에 실패했습니다.").create().show()
               Log.w("error: ", response.message().toString())
            }
         }
      })
   }

   private fun goToMain() {
      findNavController().navigate(R.id.action_withdrawalConfirmFragment_to_mainFragment)
   }
}