package com.example.heysrealprojcet.ui.join

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.heysrealprojcet.databinding.JoinPhoneFragmentBinding

class JoinPhoneFragment : Fragment() {
    private lateinit var binding: JoinPhoneFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = JoinPhoneFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}