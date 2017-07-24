package com.fitaleks.heroesthesaurus.ui

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.fitaleks.heroesthesaurus.R
import com.fitaleks.heroesthesaurus.data.MtgSet
import java.lang.Exception

/**
 * Created by alexanderkulikovskiy on 20.06.15.
 */
class CharactersListAdapter(private val mDataset: MutableList<MtgSet>) : RecyclerView.Adapter<CharactersListAdapter.CharacterViewHolder>() {
    private val VIEW_TYPE_CHARACTER_Of_THE_DAY = 0
    private val VIEW_TYPE_CHARACTER = 1

    private var useCharOfDay = false

    fun setUseCharOfDay(isUsing: Boolean) {
        useCharOfDay = isUsing
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        var characterLayout: Int
        if (viewType == VIEW_TYPE_CHARACTER_Of_THE_DAY) {
            characterLayout = R.layout.item_character_of_the_day
        } else {
            characterLayout = R.layout.character_item_photo
        }
        val view = LayoutInflater.from(parent.context)
                .inflate(characterLayout, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val mtgSet = mDataset[position]
        if (holder.itemViewType == VIEW_TYPE_CHARACTER_Of_THE_DAY) {
            holder.mName.text = holder.mName.context.getString(R.string.hero_of_the_day, mtgSet.name)
        } else {
            holder.mName.text = mtgSet.name
        }
        holder.mtgSet = mtgSet
        holder.mDescription.text = mtgSet.name
        // Api currently doesn't provide images for sets
        val setCodeForImage = mtgSet.oldCode ?: mtgSet.code
        Glide.with(holder.mImageView.context)
                .load("https://magic.wizards.com/sites/mtg/files/images/featured/EN_${setCodeForImage}_SetLogo.png")
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(object: RequestListener<String, GlideDrawable> {
                    override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onException(e: Exception?, model: String?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
                        Glide.with(holder.mImageView.context)
                                .load("https://magic.wizards.com/sites/mtg/files/images/featured/EN_${mtgSet.code}_SetLogo.png")
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into(holder.mImageView)
                        return true
                    }
                })
                .into(holder.mImageView)
    }

    override fun getItemViewType(position: Int): Int {
        return if (useCharOfDay && position == 0) VIEW_TYPE_CHARACTER_Of_THE_DAY else VIEW_TYPE_CHARACTER
    }

    override fun getItemCount(): Int {
        return this.mDataset.size
    }

    fun addCharacters(newCharacters: List<MtgSet>) {
        this.mDataset.addAll(newCharacters)
        notifyDataSetChanged()
    }

    fun clear() {
        this.mDataset.clear()
        notifyDataSetChanged()
    }

    class CharacterViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        val mImageView: ImageView by lazy { v.findViewById(R.id.img) as ImageView }
        val mDescription: TextView by lazy { v.findViewById(R.id.description) as TextView }
        val mName: TextView by lazy { v.findViewById(R.id.name) as TextView }
        var mtgSet: MtgSet? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            v.context.startActivity(Intent(v.context, CharacterDetailsActivity::class.java).apply {
                putExtra(CharacterDetailsActivity.PARAM_SET, mtgSet)
            })
        }
    }

}
