package com.example.heysrealprojcet.ui.main.content

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.StudyItemViewBinding

class StudyRecyclerViewAdapter(private val type: MutableList<String>): RecyclerView.Adapter<StudyRecyclerViewAdapter.ViewHolder>(){
    private lateinit var binding: StudyItemViewBinding

    inner class ViewHolder(private val binding: StudyItemViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(type: String) {
            binding.typeText.text = type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudyRecyclerViewAdapter.ViewHolder {
        binding = StudyItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(type[position])
    }

    override fun getItemCount(): Int {
        return type.size
    }
}