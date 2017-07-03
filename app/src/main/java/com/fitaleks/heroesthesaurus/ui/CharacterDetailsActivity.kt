package com.fitaleks.heroesthesaurus.ui

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.fitaleks.heroesthesaurus.R
import com.fitaleks.heroesthesaurus.data.MarvelCharacter

/**
 * Created by Alexander on 19.06.17.
 */
class CharacterDetailsActivity : AppCompatActivity() {

    companion object {
        val PARAM_HERO = "hero"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setupAppbar()

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CharacterDetailsFragment.newInstance(intent.getParcelableExtra<MarvelCharacter>(PARAM_HERO)))
                .commit()
    }

    private fun setupAppbar() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        val collapsingToolbar = findViewById(R.id.collapsing_toolbar_layout) as CollapsingToolbarLayout
        setSupportActionBar(toolbar)

        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapsingToolbar.setExpandedTitleColor(resources.getColor(android.R.color.transparent))
    }
}