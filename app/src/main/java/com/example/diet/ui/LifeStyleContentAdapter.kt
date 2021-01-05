package com.example.diet.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diet.R

class LifeStyleContentAdapter(
    private val context: Context,
    private val lifeStyleList: List<LifeStyle>
) : androidx.recyclerview.widget.ListAdapter<LifeStyle, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<LifeStyle>(){
        override fun areItemsTheSame(
            oldItem: LifeStyle,
            newItem: LifeStyle
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: LifeStyle,
            newItem: LifeStyle
        ): Boolean {
            return oldItem.name == newItem.name && oldItem.burnedCalorie == oldItem.burnedCalorie && oldItem.time == oldItem.time
        }
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.user_lifestyle_listitem, parent, false)
        return LifeStyleListBodyHolder(context, view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LifeStyleListBodyHolder -> holder.bind(lifeStyleList[position])
        }
    }

    override fun getItemCount(): Int {
        return lifeStyleList.size
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.activity_user_lifestyle_list
    }
}