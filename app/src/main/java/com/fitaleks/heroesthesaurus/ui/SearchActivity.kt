package com.fitaleks.heroesthesaurus.ui

import android.app.SharedElementCallback
import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Point
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.text.TextUtils
import android.transition.TransitionSet
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SearchView
import com.fitaleks.heroesthesaurus.R
import com.fitaleks.heroesthesaurus.data.MtgCard
import com.fitaleks.heroesthesaurus.util.ImeUtils
import com.fitaleks.heroesthesaurus.util.TransitionUtils
import com.fitaleks.heroesthesaurus.util.transitions.CircularReveal
import com.fitaleks.heroesthesaurus.viewmodel.CharactersViewModel

/**
 * Created by Alexander on 02.12.16.
 */
class SearchActivity : LifecycleActivity() {

    private val searchBack: ImageButton by lazy { findViewById<ImageButton>(R.id.searchback) }
    private val searchView: SearchView by lazy { findViewById<SearchView>(R.id.search_view) }
    private val recyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.search_results) }
    private val adapter = DetailsAdapter(object : DetailsAdapter.OnCardClickListener {
        override fun onClick(imageUrl: String, imageView: ImageView) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    })
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(CharactersViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupSearchView()

        setupTransitions()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onPause() {
        // needed to suppress the default window animation when closing the activity
        overridePendingTransition(0, 0)
        super.onPause()
    }

    override fun onEnterAnimationComplete() {
        // focus the search view once the enter transition finishes
        searchView.requestFocus()
        ImeUtils.showIme(searchView)
    }

    override fun onBackPressed() {
        dismiss()
    }

    private fun dismiss() {
        // clear the background else the touch ripple moves with the translation which looks bad
        searchBack.background = null
        finishAfterTransition()
    }

    private fun setupSearchView() {
        searchView.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
        searchView.imeOptions = searchView.imeOptions or EditorInfo.IME_ACTION_SEARCH or
                EditorInfo.IME_FLAG_NO_EXTRACT_UI or EditorInfo.IME_FLAG_NO_FULLSCREEN
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchFor(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (TextUtils.isEmpty(newText)) {
                    clearResults()
                }
                return true
            }
        })
    }

    private fun searchFor(query: String) {
        clearResults()
        //TODO - implement search
//        viewModel.searchForCharacters(query).observe(this, Observer { it -> it?.let { processResult(it) } })
    }

    private fun processResult(characters: List<MtgCard>) {
        if (characters.isEmpty()) {
            // Show a message indicating there are no characters for that filter type.
            // processEmptyTasks();
        } else {
            // Show the list of characters
            adapter.replaceDataWith(characters)
            ImeUtils.hideIme(searchView)
            // Set the filter label's text.
            // showFilterLabel();
        }
    }

    private fun clearResults() {
        adapter.clear()
    }

    private fun setupTransitions() {
        // grab the position that the search icon transitions in *from*
        // & use it to configure the return transition
        setEnterSharedElementCallback(object : SharedElementCallback() {
            override fun onSharedElementStart(
                    sharedElementNames: List<String>,
                    sharedElements: List<View>?,
                    sharedElementSnapshots: List<View>) {
                if (sharedElements != null && !sharedElements.isEmpty()) {
                    val searchIcon = sharedElements[0]
                    if (searchIcon.id != R.id.searchback) return
                    val centerX = (searchIcon.left + searchIcon.right) / 2
                    val hideResults = TransitionUtils.findTransition(
                            window.returnTransition as TransitionSet,
                            CircularReveal::class.java, R.id.results_container) as CircularReveal?
                    hideResults?.setCenter(Point(centerX, 0))
                }
            }
        })
    }
}
