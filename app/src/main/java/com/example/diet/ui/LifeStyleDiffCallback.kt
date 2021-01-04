package com.example.diet.ui

import androidx.recyclerview.widget.DiffUtil

class LifeStyleDiffCallback : DiffUtil.ItemCallback<LifeStyle>() {
    override fun areItemsTheSame(
        oldItem: LifeStyle,
        newItem: LifeStyle
    ): Boolean {
        return oldItem.name == newItem.name && oldItem.burnedCalorie == oldItem.burnedCalorie && oldItem.time == oldItem.time
    }

    override fun areContentsTheSame(
        oldItem: LifeStyle,
        newItem: LifeStyle
    ): Boolean {
        return oldItem.name == newItem.name && oldItem.burnedCalorie == oldItem.burnedCalorie && oldItem.time == oldItem.time
    }
}