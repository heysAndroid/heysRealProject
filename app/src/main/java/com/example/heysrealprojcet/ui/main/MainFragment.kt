package com.example.heysrealprojcet.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.MainFragmentBinding

class MainFragment : Fragment() {
    private lateinit var binding: MainFragmentBinding

    private lateinit var contestRecyclerViewAdapter: ContestRecyclerViewAdapter
    private lateinit var typeList: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 상태바 색 변경
        val mWindow = requireActivity().window
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        mWindow.statusBarColor = ContextCompat.getColor(
            requireActivity(), R.color.color_ff6e20
        )

        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavigationView.itemIconTintList = null // 아이콘이 태마색으로 변경되는 것 막음
        var menu = binding.bottomNavigationView.menu

        makeList()
        contestRecyclerViewAdapter = ContestRecyclerViewAdapter(type = typeList)
        binding.contestList.adapter = contestRecyclerViewAdapter
        binding.contestList.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        binding.jobContainer.setOnClickListener { findNavController().navigate(R.id.action_mainFragment_to_joinPopupFragment) }

        binding.studyContainer1.setOnClickListener { findNavController().navigate(R.id.action_mainFragment_to_studyFragment) }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.page_home -> {
//                    menu.findItem(R.id.page_home).setIcon(R.drawable.intent_building)
                    false
                }
                R.id.page_channel -> {
//                    findNavController().navigate(R.id.action_mainFragment_to_joinPopupFragment)
                    false
                }
                R.id.page_mypage -> {
//                    findNavController().navigate(R.id.action_mainFragment_to_joinPopupFragment)
                    false
                }
            }
            true
        }
    }

    private fun makeList() {
        typeList = mutableListOf("관심분야별", "마감 임박", "많이 찾는", "새로 열린")
    }


}