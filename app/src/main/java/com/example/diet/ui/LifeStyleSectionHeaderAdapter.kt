package com.example.diet.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diet.R

class LifeStyleSectionHeaderAdapter(
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        private const val listSize = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.user_life_style_list_item, parent, false)
        return LifeStyleSectionHeaderHolder(view, context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LifeStyleSectionHeaderHolder -> holder.bind()
        }
    }

    override fun getItemCount(): Int = listSize
}
