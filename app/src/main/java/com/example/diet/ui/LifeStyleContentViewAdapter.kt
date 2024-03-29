package com.example.diet.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class LifeStyleContentViewAdapter : ListAdapter<LifeStyle, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<LifeStyle>() {
        override fun areItemsTheSame(
            oldItem: LifeStyle,
            newItem: LifeStyle
        ): Boolean = areContentsTheSame(oldItem, newItem)

        override fun areContentsTheSame(
            oldItem: LifeStyle,
            newItem: LifeStyle
        ): Boolean =
            oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LifeStyleContentViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LifeStyleContentViewHolder -> holder.bind(getItem(position))
        }
    }
}
