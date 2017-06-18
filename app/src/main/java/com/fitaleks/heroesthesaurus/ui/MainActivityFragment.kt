package com.fitaleks.heroesthesaurus.ui

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fitaleks.heroesthesaurus.R
import com.fitaleks.heroesthesaurus.data.MarvelCharacter
import com.fitaleks.heroesthesaurus.viewmodel.CharactersViewModel
import java.util.*

class MainActivityFragment : LifecycleFragment() {

    private val recyclerView: RecyclerView by lazy {
        view?.findViewById(R.id.recycler_view) as RecyclerView
    }
    private val swipeRefresh: SwipeRefreshLayout by lazy {
        view?.findViewById(R.id.swipe_refresh) as SwipeRefreshLayout
    }

    private var mAdapter: CharactersListAdapter = CharactersListAdapter(ArrayList<MarvelCharacter>())
    private var mLinearLayoutManager: LinearLayoutManager? = null
    private var loading = true
    private var previousTotal = 0

    private var firstVisibleItem: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0

    private val listen = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
        }

        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            visibleItemCount = recyclerView!!.childCount
            totalItemCount = mLinearLayoutManager!!.itemCount
            firstVisibleItem = mLinearLayoutManager!!.findFirstVisibleItemPosition()

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false
                    previousTotal = totalItemCount
                }
            }
            if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + 2) {
                // End has been reached

                //                getObservable().observeOn(AndroidSchedulers.mainThread())
                //                        .subscribe(MainActivityFragment.this::updateAdapter);

                loading = true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLinearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = mLinearLayoutManager
        recyclerView.addOnScrollListener(listen)
        recyclerView.adapter = mAdapter
        swipeRefresh.isEnabled = false

        val charactersViewModel = ViewModelProviders.of(this).get(CharactersViewModel::class.java)
        charactersViewModel.getCharactersData().observe(this, android.arch.lifecycle.Observer { t -> t?.let { mAdapter.addCharacters(it) } })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onDestroyView() {
        recyclerView.removeOnScrollListener(listen)
        super.onDestroyView()
    }

    companion object {
        fun newInstance(): MainActivityFragment {
            return MainActivityFragment()
        }
    }
}
