package com.example.diet.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class LifeStyleSectionHeaderViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val listSize = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LifeStyleSectionHeaderView(parent.context)
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return LifeStyleSectionHeaderViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LifeStyleSectionHeaderViewHolder -> holder.bind()
        }
    }

    override fun getItemCount(): Int = listSize
}
