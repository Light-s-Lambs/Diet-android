package com.example.diet.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class MealContentViewAdapter : ListAdapter<Meal, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(
            oldItem: Meal,
            newItem: Meal
        ): Boolean = areContentsTheSame(oldItem, newItem)

        override fun areContentsTheSame(
            oldItem: Meal,
            newItem: Meal
        ): Boolean =
            oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MealContentViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MealContentViewHolder -> holder.bind(getItem(position))
        }
    }
}
