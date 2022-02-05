package com.example.heysrealprojcet.ui.join

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.heysrealprojcet.databinding.JoinPhoneFragmentBinding

class JoinPhoneFragment : Fragment() {
    private lateinit var binding: JoinPhoneFragmentBinding
    private val viewModel: JoinPhoneViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = JoinPhoneFragmentBinding.inflate(inflater, container, false)
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.okButton.setOnClickListener {
            with(binding) {
                description1.text = viewModel.phone.value
                description2.text = "문자로 보내드린 6자리 인증번호를 적어주세요."
                phoneInput.hint = "인증번호(6자리)"
                timeText.visibility = View.VISIBLE
                resendButton.visibility = View.VISIBLE
            }
        }
    }
}