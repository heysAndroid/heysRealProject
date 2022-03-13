package com.example.heysrealprojcet.ui.join.phone

import android.content.Context
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.JoinPhoneFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

class JoinPhoneFragment : Fragment() {
    private lateinit var binding: JoinPhoneFragmentBinding
    private val viewModel: JoinPhoneViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
                             ): View? {
        binding = JoinPhoneFragmentBinding.inflate(inflater, container, false)
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState) // livedata 사용할 때 객체 범위를 반드시 지정해줘야함!!
        binding.lifecycleOwner = this
        binding.okButton.setOnClickListener { goToPhoneVerification() }
        binding.phoneInput.addTextChangedListener(PhoneNumberFormattingTextWatcher("KR"))

        // 화면 들어오자마자 키보드 보이기
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding.phoneInput, 0)
    }

    private fun goToPhoneVerification() {
        findNavController().navigate(
            R.id.action_joinPhoneFragment_to_phoneVerificationFragment,
            bundleOf("phoneNumber" to viewModel.phoneNumber.value))
    }
}