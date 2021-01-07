package com.example.diet.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diet.R

class LifeStyleSectionHeaderAdapter(
    private val context: Context
) : ListAdapter<LifeStyle, RecyclerView.ViewHolder>(
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
        val view =
            LayoutInflater.from(context).inflate(R.layout.user_lifestyle_list_item, parent, false)
        return LifeStyleSectionHeaderHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LifeStyleSectionHeaderHolder -> holder.bind()
        }
    }
}
