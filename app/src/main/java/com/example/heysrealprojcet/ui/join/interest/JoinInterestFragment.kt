package com.example.heysrealprojcet.ui.join.interest

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.JoinInterestFragmentBinding
import com.example.heysrealprojcet.model.User
import com.example.heysrealprojcet.model.UserCategory
import com.example.heysrealprojcet.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JoinInterestFragment : Fragment() {
   private lateinit var binding: JoinInterestFragmentBinding
   private val viewModel: JoinInterestViewModel by viewModels()

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = JoinInterestFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      binding.okButton.setOnClickListener { requestSignUp() }
   }

   private fun goToMain() {
      findNavController().navigate(R.id.action_joinInterestFragment_to_mainFragment)
   }

   private fun requestSignUp() {
      val user = User(
         name = UserPreference.name,
         phone = UserPreference.phoneNumber,
         age = UserPreference.age,
         gender = UserPreference.gender,
         password = UserPreference.password,
         userCategories = listOf(UserCategory(preference = 1, categoryId = 2)))

      viewModel.signup(user)
      viewModel.isSuccess.observe(viewLifecycleOwner, { isSuccess ->
         viewModel.accessToken.observe(viewLifecycleOwner, {
            val alert = AlertDialog.Builder(requireContext())
            if (isSuccess) {
               alert.setTitle("${user.name}님 회원가입 성공")
                  .setMessage("accessToken: ${viewModel.accessToken.value}")
                  .setPositiveButton("확인") { _, _ ->
                     goToMain()
                  }.create().show()
            } else {
               alert.setTitle("회원가입 실패").setMessage("회원가입에 실패했습니다").create().show()
            }
         })
      })
   }
}