package com.example.heysrealprojcet.ui.user.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.heysrealprojcet.databinding.SettingPasswordChangeFragmentBinding

class SettingPasswordChangeFragment : Fragment() {
   private lateinit var binding: SettingPasswordChangeFragmentBinding
   private val viewModel: SettingPasswordChangeViewModel by viewModels()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = SettingPasswordChangeFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      binding.okButton.setOnClickListener { goToSetting() }
   }

   private fun goToSetting() {
      TODO("비밀번호 변경 후 설정 페이지로 다시 이동")
   }
}