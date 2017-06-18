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
import com.fitaleks.heroesthesaurus.data.MarvelCharacter

/**
 * Created by alexanderkulikovskiy on 20.06.15.
 */
class CharactersListAdapter(private val mDataset: MutableList<MarvelCharacter>) : RecyclerView.Adapter<CharactersListAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.character_item_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val character = mDataset[position]
        holder.mName.text = character.name
        holder.mDescription.text = character.description
        Glide.with(holder.mImageView.context)
                .load("${character.thumbnail?.path}.${character.thumbnail?.extension}")
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.mImageView)
    }

    override fun getItemCount(): Int {
        return this.mDataset.size
    }

    fun addCharacters(newCharacters: List<MarvelCharacter>) {
        this.mDataset.addAll(newCharacters)
        notifyDataSetChanged()
    }

    fun clear() {
        this.mDataset.clear()
        notifyDataSetChanged()
    }

    class PhotoViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val mImageView: ImageView by lazy { v.findViewById(R.id.img) as ImageView }
        val mDescription: TextView by lazy { v.findViewById(R.id.description) as TextView }
        val mName: TextView by lazy { v.findViewById(R.id.name) as TextView }
    }

}
