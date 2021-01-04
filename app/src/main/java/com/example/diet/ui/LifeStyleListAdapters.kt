package com.example.diet.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diet.R
import java.util.*

class LifeStyleListHeaderAdapter(
    private val context: Context
) : androidx.recyclerview.widget.ListAdapter<LifeStyle, RecyclerView.ViewHolder>(
    LifeStyleDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_user_lifestyle_list, parent, false)
        return LifeStyleListHeaderHolder(context, view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LifeStyleListHeaderHolder -> holder.bind()
        }
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.activity_user_lifestyle_list
    }
}

class LifeStyleListBodyAdapter(
    private val context: Context,
    private val lifeStyleList: ArrayList<LifeStyle>
) : androidx.recyclerview.widget.ListAdapter<LifeStyle, RecyclerView.ViewHolder>(
    LifeStyleDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_user_lifestyle_list, parent, false)
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