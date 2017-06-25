package com.fitaleks.heroesthesaurus.ui

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fitaleks.heroesthesaurus.R

/**
 * Created by Alexander on 19.06.17.
 */
class CharacterDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_character_details, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val heroDetailsRecyclerView = view?.findViewById(R.id.details_nested_scroll) as RecyclerView
        val layoutManager = GridLayoutManager(context, 2)
//        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int) = if (position == 0) 2 else 1
//        }
        heroDetailsRecyclerView.layoutManager = layoutManager
        heroDetailsRecyclerView.setHasFixedSize(true)
        heroDetailsRecyclerView.adapter = DetailsAdapter(30)
        heroDetailsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    (activity.findViewById(R.id.appbar) as AppBarLayout).setExpanded(true)
                }
            }
        })
    }
}