package com.fitaleks.heroesthesaurus.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fitaleks.heroesthesaurus.R

/**
 * Created by Alexander on 19.06.17.
 */
class DetailsAdapter(val numOfElems: Int) : RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder>() {

    class DetailsViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val mName: TextView by lazy { v.findViewById(R.id.name) as TextView }
    }

    override fun getItemCount(): Int = numOfElems

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_item_photo, parent, false)
        return DetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        holder.mName.text = "a lot of some text just to check some of my ideassome of my ideassome of my ideassome of my ideas$position"
    }
}