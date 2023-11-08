package com.example.heysrealprojcet.ui.user.setting.withdrawal

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
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.WithdrawalConfirmFragmentBinding
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.ui.intro.IntroActivity
import com.example.heysrealprojcet.util.UserPreference
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
      viewModel.withdrawal("Bearer ${UserPreference.accessToken}", reason).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               val intent = Intent(requireContext(), IntroActivity::class.java)
               startActivity(intent)
               requireActivity().finish()
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

   private fun goToMain() {
      findNavController().navigate(R.id.action_withdrawalConfirmFragment_to_mainFragment)
   }
}