package com.fitaleks.heroesthesaurus.ui

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.fitaleks.heroesthesaurus.R
import com.fitaleks.heroesthesaurus.viewmodel.CharactersViewModel

/**
 * Created by Alexander on 19.06.17.
 */
class CharacterDetailsFragment : LifecycleFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_character_details, container, false)
    }

    private val adapter = DetailsAdapter(0)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val heroDetailsRecyclerView = view?.findViewById(R.id.details_nested_scroll) as RecyclerView
        val layoutManager = GridLayoutManager(context, 2)
//        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int) = if (position == 0) 2 else 1
//        }
        heroDetailsRecyclerView.layoutManager = layoutManager
        heroDetailsRecyclerView.setHasFixedSize(true)
        heroDetailsRecyclerView.adapter = adapter
        heroDetailsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    (activity.findViewById(R.id.appbar) as AppBarLayout).setExpanded(true)
                }
            }
        })
        val charactersViewModel = ViewModelProviders.of(this).get(CharactersViewModel::class.java)
        charactersViewModel.getCharacter(arguments.getLong(PARAM_CHARACTER_ID))
                .observe(this, android.arch.lifecycle.Observer { t -> t?.let {
                    val appbarImageView = activity.findViewById(R.id.details_appbar_image) as ImageView
                    Glide.with(this)
                            .load(t.getStandardImagePath())
                            .into(appbarImageView)
                    (activity as AppCompatActivity).supportActionBar?.title = t.name
                    adapter.replaceDataWith(t.comics.items)
                } })
    }

    companion object {
        val PARAM_CHARACTER_ID = "character_id"
        fun newInstance(characterId: Long): CharacterDetailsFragment = CharacterDetailsFragment().apply {
            arguments = Bundle().apply { putLong(PARAM_CHARACTER_ID, characterId) }
        }
    }
}