package com.example.heysrealprojcet.ui.user.myPage

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.MyPageFragmentBinding
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : Fragment() {
   private lateinit var binding: MyPageFragmentBinding
   private val viewModel: MyPageViewModel by viewModels()

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = MyPageFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      getMyInfo()

      with(binding) {
         goToSetting.setOnClickListener { goToSetting() }
         editProfile.setOnClickListener { goToProfileEdit() }
         engagedChannelContainer.setOnClickListener { goToEngagedChannel() }
         waitingChannelContainer.setOnClickListener { goToWaitingChannel() }
      }
   }

   private fun goToSetting() {
      findNavController().navigate(R.id.action_myPageFragment_to_settingFragment)
   }

   private fun goToEngagedChannel() {
      findNavController().navigate(R.id.action_myPageFragment_to_engagedChannelListFragment)
   }

   private fun goToWaitingChannel() {
      findNavController().navigate(R.id.action_myPageFragment_to_waitingChannelListFragment)
   }

   private fun goToProfileEdit() {
      findNavController().navigate(R.id.action_myPageFragment_to_profileEditFragment)
   }

   private fun getMyInfo() {
      val token = UserPreference.accessToken
      viewModel.getMyInfo("Bearer $token")
      viewModel.response.observe(viewLifecycleOwner) { response ->
         val alert = AlertDialog.Builder(requireContext())

         when (response) {
            is NetworkResult.Success -> {
               // TODO 로딩 성공시 처리
            }

            is NetworkResult.Error -> {
               alert.setTitle("마이페이지 로딩 실패").setMessage("마이페이지 로딩에 실패했습니다.").create().show()
            }

            is NetworkResult.Loading -> {
               alert.setTitle("마이페이지 로딩 중").setMessage("마이페이지 로딩이 지연되고 있습니다.").create().show()
            }
         }
      }
   }
}