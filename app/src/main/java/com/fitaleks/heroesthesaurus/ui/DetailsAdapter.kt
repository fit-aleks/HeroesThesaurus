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
import com.fitaleks.heroesthesaurus.data.MarvelComics

/**
 * Created by Alexander on 19.06.17.
 */
class DetailsAdapter : RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder>() {

    private var listOfComics: List<MarvelComics>? = null

    class DetailsViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val mName by lazy { v.findViewById(R.id.title) as TextView }
        val img by lazy { v.findViewById(R.id.cover) as ImageView }
    }

    override fun getItemCount(): Int = listOfComics?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comics, parent, false)
        return DetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        holder.mName.text = listOfComics?.get(position)?.title ?: ""
        Glide.with(holder.img.context)
                .load(listOfComics?.get(position)?.getStandardImagePath())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.img)
    }

    fun replaceDataWith(comics: List<MarvelComics>) {
        listOfComics = comics
        notifyDataSetChanged()
    }
}