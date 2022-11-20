package com.example.heysrealprojcet.ui.user.setting.delete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.heysrealprojcet.databinding.SettingDeleteAccountFragmentBinding

class SettingDeleteAccountFragment : Fragment() {
   private lateinit var binding : SettingDeleteAccountFragmentBinding

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = SettingDeleteAccountFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }
}