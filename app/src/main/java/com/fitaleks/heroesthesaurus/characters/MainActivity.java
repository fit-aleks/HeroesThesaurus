package com.fitaleks.heroesthesaurus.characters;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fitaleks.heroesthesaurus.R;
import com.fitaleks.heroesthesaurus.data.source.CharactersRepository;
import com.fitaleks.heroesthesaurus.data.source.local.CharatersLocalDataSource;
import com.fitaleks.heroesthesaurus.data.source.remote.CharactersRemoteDataSource;
import com.fitaleks.heroesthesaurus.util.ActivityUtils;
import com.fitaleks.heroesthesaurus.util.schedulers.SchedulerProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SEARCH = 42;

    private CharactersPresenter charactersPresenter;
    @BindView(R.id.toolbar) Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        MainActivityFragment fragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            fragment = MainActivityFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.contentFrame);
        }

        charactersPresenter = new CharactersPresenter(CharactersRepository.getInstance(CharactersRemoteDataSource.getInstance(),
                CharatersLocalDataSource.getInstance(this, SchedulerProvider.getInstance())),
                fragment,
                SchedulerProvider.getInstance());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_search) {
            View searchMenuView = toolbar.findViewById(R.id.menu_search);
            Bundle options = ActivityOptions.makeSceneTransitionAnimation(this, searchMenuView,
                    getString(R.string.transition_search_back)).toBundle();
            startActivityForResult(new Intent(this, SearchActivity.class), RC_SEARCH, options);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
