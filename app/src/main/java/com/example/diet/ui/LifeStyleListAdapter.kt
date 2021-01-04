package com.example.diet.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
            0 -> HolderHeader(view)
            else -> Holder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HolderHeader -> holder.bind()
            is Holder -> holder.bind(lifeStyleList[position - 1])
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

    inner class HolderHeader(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val lifeStyleName: TextView =
            itemView.findViewById<TextView>(R.id.lifeStyleNameTextView)
        private val lifeStyleTime: TextView =
            itemView.findViewById<TextView>(R.id.lifeStyleTimeTextView)
        private val lifeStyleBurnedCalorie: TextView =
            itemView.findViewById<TextView>(R.id.lifeStyleBurnedCalorieTextView)

        fun bind() {
            lifeStyleName.maxLines = 2
            lifeStyleName.text = context.getString(R.string.userLifeStyleListLifeStyleNameLabel)
            lifeStyleTime.text = context.getString(R.string.userLifeStyleListLifeStyleTimeLabel)
            lifeStyleBurnedCalorie.maxLines = 2
            lifeStyleBurnedCalorie.text =
                context.getString(R.string.userLifeStyleListBurnedCalorieLabel)
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val lifeStyleName: TextView =
            itemView.findViewById<TextView>(R.id.lifeStyleNameTextView)
        private val lifeStyleTime: TextView =
            itemView.findViewById<TextView>(R.id.lifeStyleTimeTextView)
        private val lifeStyleBurnedCalorie: TextView =
            itemView.findViewById<TextView>(R.id.lifeStyleBurnedCalorieTextView)

        fun bind(lifeStyle: LifeStyle) {
            lifeStyleName.text = lifeStyle.name
            lifeStyleTime.text = lifeStyle.time
            lifeStyleBurnedCalorie.text = lifeStyle.burnedCalorie
        }
    }

}
