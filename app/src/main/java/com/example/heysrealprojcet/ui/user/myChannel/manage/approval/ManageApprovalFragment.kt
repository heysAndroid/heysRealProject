package com.example.heysrealprojcet.ui.user.myChannel.manage.approval

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ManageApprovalFragmentBinding
import com.example.heysrealprojcet.model.ApprovalList

class ManageApprovalFragment : Fragment() {
   private lateinit var binding: ManageApprovalFragmentBinding
   private lateinit var manageApprovalViewAdapter: ManageApprovalViewAdapter
   private lateinit var approvalList: MutableList<ApprovalList>

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ManageApprovalFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      makeList()
      manageApprovalViewAdapter = ManageApprovalViewAdapter(approvalList)
      binding.approvalList.adapter = manageApprovalViewAdapter
      binding.approvalList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
   }

   private fun makeList() {
      approvalList = mutableListOf(
         ApprovalList(R.drawable.bg_circle_e5e5e5, "이름", "요청한지 1일"),
         ApprovalList(R.drawable.bg_circle_e5e5e5, "이름", "요청한지 1일")
      )
   }
}