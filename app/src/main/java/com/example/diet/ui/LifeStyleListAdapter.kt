package com.example.diet.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diet.R
import java.util.*

class LifeStyleListAdapter(
    private val context: Context,
    private val lifeStyleList: ArrayList<LifeStyle>
) : androidx.recyclerview.widget.ListAdapter<LifeStyle, RecyclerView.ViewHolder>(
    LifeStyleDiffCallback()
) {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_user_lifestyle_list, parent, false)
        return when (viewType) {
            0 -> LifeStyleListHeaderHolder(context, view)
            else -> LifeStyleListHolder(context, view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LifeStyleListHeaderHolder -> holder.bind()
            is LifeStyleListHolder -> holder.bind(lifeStyleList[position - 1])
        }
    }

    override fun getItemCount(): Int {
        return lifeStyleList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_HEADER
            else -> TYPE_ITEM
        }
    }
}
