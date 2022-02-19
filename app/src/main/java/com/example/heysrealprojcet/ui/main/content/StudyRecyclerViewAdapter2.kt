package com.example.heysrealprojcet.ui.main.content

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.StudyItem2ViewBinding

class StudyRecyclerViewAdapter2(private val type2: MutableList<String>): RecyclerView.Adapter<StudyRecyclerViewAdapter2.ViewHolder>() {
    private lateinit var binding: StudyItem2ViewBinding

    inner class ViewHolder(private val binding: StudyItem2ViewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(type2: String) {
            binding.typeText.text = type2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudyRecyclerViewAdapter2.ViewHolder {
        binding = StudyItem2ViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(type2[position])
    }

    override fun getItemCount(): Int {
        return type2.size
    }
}