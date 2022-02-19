package com.example.heysrealprojcet.ui.main.content

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.StudyFragmentBinding

class StudyFragment : Fragment() {
    private lateinit var binding: StudyFragmentBinding

    private lateinit var studyRecyclerViewAdapter: StudyRecyclerViewAdapter
    private lateinit var studyRecyclerViewAdapter2: StudyRecyclerViewAdapter2
    private lateinit var typeList: MutableList<String>
    private lateinit var typeList2: MutableList<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = StudyFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        makeList()
        studyRecyclerViewAdapter = StudyRecyclerViewAdapter(type = typeList)
        binding.studyContentList1.adapter = studyRecyclerViewAdapter
        binding.studyContentList1.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        studyRecyclerViewAdapter2 = StudyRecyclerViewAdapter2(type2 = typeList2)
        binding.studyContentList2.adapter = studyRecyclerViewAdapter2
        binding.studyContentList2.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

    }

    private fun makeList() {
        typeList = mutableListOf("관심 분야", "마감 임박", "많이 찾는", "새로 열린")
        typeList2 = mutableListOf("개설한지 6일된 헤이즈", "개설한지 90일된 헤이즈", "개설한지 7일된 헤이즈")
    }
}