package com.example.diet.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class LifeStyleSectionHeaderViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val listSize = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LifeStyleSectionHeaderViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LifeStyleSectionHeaderViewHolder -> holder.bind()
        }
    }

    override fun getItemCount(): Int = listSize
}
