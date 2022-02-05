package com.example.heysrealprojcet.ui.join

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.heysrealprojcet.databinding.JoinFragmentBinding

class JoinFragment : Fragment() {
    private lateinit var binding: JoinFragmentBinding
    private lateinit var joinPagerAdapter: JoinPagerAdatper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = JoinFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        joinPagerAdapter = JoinPagerAdatper()
    }
}