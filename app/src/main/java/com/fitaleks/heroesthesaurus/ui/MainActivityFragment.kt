package com.fitaleks.heroesthesaurus.ui

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fitaleks.heroesthesaurus.PATH_TO_ICONS
import com.fitaleks.heroesthesaurus.R
import com.fitaleks.heroesthesaurus.data.MtgSet
import com.fitaleks.heroesthesaurus.viewmodel.CharactersViewModel
import java.util.*

class MainActivityFragment : LifecycleFragment() {

    private val charactersRecyclerView: RecyclerView by lazy {
        view?.findViewById(R.id.recycler_view) as RecyclerView
    }
    private val swipeRefresh: SwipeRefreshLayout by lazy {
        view?.findViewById(R.id.swipe_refresh) as SwipeRefreshLayout
    }

    private var mAdapter: CharactersListAdapter = CharactersListAdapter(ArrayList<MtgSet>()).apply { setUseCharOfDay(true) }
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

    private val TAG: String = MainActivityFragment::class.java.simpleName

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLinearLayoutManager = LinearLayoutManager(activity)
        charactersRecyclerView.layoutManager = mLinearLayoutManager
        charactersRecyclerView.addOnScrollListener(listen)
        charactersRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        charactersRecyclerView.adapter = mAdapter
        swipeRefresh.isEnabled = false

        val appBarView: AppBarLayout? = activity.findViewById(R.id.appbar) as AppBarLayout
        charactersRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (charactersRecyclerView.computeVerticalScrollOffset() == 0) {
                    appBarView?.elevation = 0f
                } else {
                    appBarView?.elevation = resources.getDimension(R.dimen.appbar_elevation)
                }
            }
        })

//        val charactersViewModel = ViewModelProviders.of(this).get(CharactersViewModel::class.java)
//        charactersViewModel.getCharactersData()?.observe(this, android.arch.lifecycle.Observer { t -> t?.let { mAdapter.addCharacters(it) } })

        val setsViewModel = ViewModelProviders.of(this).get(CharactersViewModel::class.java)
        val listOfAllIcons = resources.assets.list(PATH_TO_ICONS)
        setsViewModel.getSets().observe(this, android.arch.lifecycle.Observer { t ->
            t?.filter { set ->
                if (!listOfAllIcons.contains(set.imageName())) {
                    Log.d(TAG, "${set.imageName()} has no icon")
                }

                set.type != "promo" && set.type != "reprint" && set.type != "box" && listOfAllIcons.contains(set.imageName())
            }
                    ?.let {
                        Log.d(TAG, "num of elems = ${it.size}")
                        mAdapter.addCharacters(it)
                    }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onDestroyView() {
        charactersRecyclerView.clearOnScrollListeners()
        super.onDestroyView()
    }

    companion object {
        fun newInstance(): MainActivityFragment {
            return MainActivityFragment()
        }
    }
}
