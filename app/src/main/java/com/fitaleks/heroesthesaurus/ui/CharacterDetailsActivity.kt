package com.fitaleks.heroesthesaurus.ui

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.fitaleks.heroesthesaurus.R

/**
 * Created by Alexander on 19.06.17.
 */
class CharacterDetailsActivity : AppCompatActivity() {

    companion object {
        val PARAM_HERO_IMAGE_URL = "image_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setupAppbar()

        val appbarImageView = findViewById(R.id.details_appbar_image) as ImageView
        Glide.with(this)
                .load(intent.getStringExtra(PARAM_HERO_IMAGE_URL))
                .into(appbarImageView)
    }

    private fun setupAppbar() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        val collapsingToolbar = findViewById(R.id.collapsing_toolbar_layout) as CollapsingToolbarLayout
        setSupportActionBar(toolbar)

        supportActionBar?.title = "Some hero name"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapsingToolbar.setExpandedTitleColor(resources.getColor(android.R.color.transparent))
    }
}