package com.fitaleks.heroesthesaurus.ui

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
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
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.fitaleks.heroesthesaurus.R
import com.fitaleks.heroesthesaurus.data.MarvelCharacter
import com.fitaleks.heroesthesaurus.viewmodel.CharactersViewModel

/**
 * Created by Alexander on 19.06.17.
 */
class CharacterDetailsFragment : LifecycleFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_character_details, container, false)
    }

    private val adapter = DetailsAdapter()

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val heroDetailsRecyclerView = view?.findViewById(R.id.details_nested_scroll) as RecyclerView
        val layoutManager = GridLayoutManager(context, 2)

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
        val character = arguments.getParcelable<MarvelCharacter>(PARAM_CHARACTER)
        val appbarImageView = activity.findViewById(R.id.details_appbar_image) as ImageView
        Glide.with(this)
                .load(character.getStandardImagePath())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(appbarImageView)
        (activity as AppCompatActivity).supportActionBar?.title = character.name

        val comicsViewModel = ViewModelProviders.of(this).get(CharactersViewModel::class.java)
        comicsViewModel.getComicsByCharacter(character.marvelId)
                .observe(this, Observer { t ->
                    t?.let {
                        adapter.replaceDataWith(t)
                    }
                })
    }

    companion object {
        val PARAM_CHARACTER = "character"
        fun newInstance(character: MarvelCharacter): CharacterDetailsFragment = CharacterDetailsFragment().apply {
            arguments = Bundle().apply { putParcelable(PARAM_CHARACTER, character) }
        }
    }
}