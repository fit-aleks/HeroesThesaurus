package com.fitaleks.heroesthesaurus.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.fitaleks.heroesthesaurus.R
import com.fitaleks.heroesthesaurus.ui.widget.ElasticDragDismissFrameLayout
import java.lang.Exception

/**
 * Created by alex206512252 on 7/27/17.
 */
class ImageActivity : AppCompatActivity(), ElasticDragDismissFrameLayout.Callback {
    companion object {
        val PARAM_URL = "param_url"
    }

    private val draggable by lazy { findViewById<ElasticDragDismissFrameLayout>(R.id.draggable_frame) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_image)
        val string = intent.getStringExtra(PARAM_URL)
        val image = findViewById<ImageView>(R.id.fragment_full_screen_imageView)
        supportPostponeEnterTransition()
        Glide.with(this)
                .load(string)
                .listener(object : RequestListener<String, GlideDrawable>{
                    override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }

                    override fun onException(e: Exception?, model: String?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
                        return false
                    }
                })
                .into(image)


//        val puller = findViewById<PullBackLayout>(R.id.draggable_frame)
//        puller.setCallback(this)
        draggable.setCallback(this)
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPullStart() {

    }

    override fun onPull(progress: Float) {

    }

    override fun onPullCancel() {

    }

    override fun onPullComplete() {
        supportFinishAfterTransition()
    }
}