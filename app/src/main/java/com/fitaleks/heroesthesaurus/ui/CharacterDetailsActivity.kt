package com.fitaleks.heroesthesaurus.ui

import android.os.Bundle
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

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Some hero name"

        val appbarImageView = findViewById(R.id.details_appbar_image) as ImageView
        Glide.with(this)
                .load(intent.getStringExtra(PARAM_HERO_IMAGE_URL))
                .into(appbarImageView)
    }
}