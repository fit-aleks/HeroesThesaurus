package com.fitaleks.heroesthesaurus.characters

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.fitaleks.heroesthesaurus.R
import com.fitaleks.heroesthesaurus.data.source.CharactersRepository
import com.fitaleks.heroesthesaurus.data.source.local.CharatersLocalDataSource
import com.fitaleks.heroesthesaurus.data.source.remote.CharactersRemoteDataSource
import com.fitaleks.heroesthesaurus.util.addFragmentToActivity
import com.fitaleks.heroesthesaurus.util.schedulers.SchedulerProvider

class MainActivity : AppCompatActivity() {

    private var charactersPresenter: CharactersPresenter? = null
    private val toolbar: Toolbar by lazy {findViewById(R.id.toolbar) as Toolbar}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        var fragment: MainActivityFragment? = supportFragmentManager.findFragmentById(R.id.contentFrame) as MainActivityFragment?
        if (fragment == null) {
            fragment = MainActivityFragment.newInstance()
            addFragmentToActivity(fragment, R.id.contentFrame)
        }

        charactersPresenter = CharactersPresenter(CharactersRepository.instance(CharactersRemoteDataSource.instance,
                CharatersLocalDataSource(this, SchedulerProvider.getInstance())),
                fragment,
                SchedulerProvider.getInstance())

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.itemId == R.id.menu_search) {
            val searchMenuView = toolbar.findViewById(R.id.menu_search)
            val options = ActivityOptions.makeSceneTransitionAnimation(this, searchMenuView,
                    getString(R.string.transition_search_back)).toBundle()
            startActivityForResult(Intent(this, SearchActivity::class.java), RC_SEARCH, options)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val RC_SEARCH = 42
    }
}
