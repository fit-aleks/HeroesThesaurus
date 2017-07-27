package com.fitaleks.heroesthesaurus.ui

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.fitaleks.heroesthesaurus.FULL_PATH_TO_ICONS
import com.fitaleks.heroesthesaurus.R
import com.fitaleks.heroesthesaurus.data.MtgSet
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by alexanderkulikovskiy on 20.06.15.
 */
class SetsListAdapter(private val mDataset: MutableList<MtgSet>) : RecyclerView.Adapter<SetsListAdapter.CharacterViewHolder>() {
    private val VIEW_TYPE_CHARACTER_Of_THE_DAY = 0
    private val VIEW_TYPE_CHARACTER = 1

    private var useCharOfDay = false
    private val releaseDateTextFormatter = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault())

    fun setUseCharOfDay(isUsing: Boolean) {
        useCharOfDay = isUsing
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val characterLayout: Int = if (viewType == VIEW_TYPE_CHARACTER_Of_THE_DAY) {
            R.layout.item_latest_set
        } else {
            R.layout.item_set
        }
        val view = LayoutInflater.from(parent.context).inflate(characterLayout, parent, false)
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
        mtgSet.block?.let { holder.mDescription.text = mtgSet.block }


        holder.releaseDate.text = holder.releaseDate.context.getString(R.string.set_release_date, releaseDateTextFormatter.format(mtgSet.releaseDate))
        Glide.with(holder.mImageView.context)
                .load("$FULL_PATH_TO_ICONS${mtgSet.imageName()}")
                .fitCenter()
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
        val mImageView: ImageView by lazy { v.findViewById<ImageView>(R.id.img) }
        val mDescription: TextView by lazy { v.findViewById<TextView>(R.id.description) }
        val mName: TextView by lazy { v.findViewById<TextView>(R.id.name) }
        val releaseDate: TextView by lazy { v.findViewById<TextView>(R.id.set_release_date) }
        var mtgSet: MtgSet? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            v.context.startActivity(Intent(v.context, SetContentActivity::class.java).apply {
                putExtra(SetContentActivity.PARAM_SET, mtgSet)
            })
        }
    }

}
