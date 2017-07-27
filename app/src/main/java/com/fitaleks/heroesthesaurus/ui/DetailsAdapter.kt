package com.fitaleks.heroesthesaurus.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.fitaleks.heroesthesaurus.R
import com.fitaleks.heroesthesaurus.data.MtgCard

/**
 * Created by Alexander on 19.06.17.
 */
class DetailsAdapter(val clickListener: OnCardClickListener) : RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder>() {

    private var listOfCards: List<MtgCard>? = null

    class DetailsViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val mName by lazy { v.findViewById<TextView>(R.id.title) }
        val img by lazy { v.findViewById<ImageView>(R.id.cover) }
    }

    override fun getItemCount(): Int = listOfCards?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comics, parent, false)
        return DetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        holder.mName.text = listOfCards?.get(position)?.name ?: ""
//        holder.cardUrl = listOfCards?.get(position)?.imageUrl
        Glide.with(holder.img.context)
                .load(listOfCards?.get(position)?.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.img)
        holder.itemView.setOnClickListener {
            clickListener.onClick(listOfCards?.get(position)?.imageUrl ?: "", holder.img)
        }
    }

    fun replaceDataWith(cards: List<MtgCard>) {
        listOfCards = cards
        notifyDataSetChanged()
    }

    fun clear() {
        listOfCards = null
        notifyDataSetChanged()
    }

    interface OnCardClickListener {
        fun onClick(imageUrl: String, imageView: ImageView)
    }
}