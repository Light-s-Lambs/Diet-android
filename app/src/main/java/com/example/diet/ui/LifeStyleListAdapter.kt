package com.example.diet.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diet.R
import java.util.ArrayList

class LifeStyleListAdapter(
    private val context: Context,
    private val lifeStyleList: ArrayList<LifeStyle>
) :
    RecyclerView.Adapter<LifeStyleListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_user_lifestyle_list, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return lifeStyleList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(lifeStyleList[position])
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
