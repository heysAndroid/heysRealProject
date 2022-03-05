package com.example.heysrealprojcet.ui.join.interest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.JoinInterestFragmentBinding
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
        binding.okButton.setOnClickListener { goToMain() }
    }

    private fun goToMain() {
        viewModel.signup()
        findNavController().navigate(R.id.action_joinInterestFragment_to_mainFragment)
    }
}