package com.example.diet.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class MealContentsViewAdapter : ListAdapter<Meal, RecyclerView.ViewHolder>(
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
        val view = MealContentsView(parent.context)
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return MealContentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MealContentsViewHolder -> holder.bind(getItem(position))
        }
    }
}
