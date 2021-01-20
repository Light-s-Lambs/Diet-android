package com.example.diet.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MealSectionHeaderViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val listSize: Int = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MealSectionHeaderViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MealSectionHeaderViewHolder -> holder.bind()
        }
    }

    override fun getItemCount(): Int = listSize
}
