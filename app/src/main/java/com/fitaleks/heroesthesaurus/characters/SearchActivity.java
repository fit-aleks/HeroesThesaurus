package com.fitaleks.heroesthesaurus.characters;

import android.app.SharedElementCallback;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.transition.TransitionSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.fitaleks.heroesthesaurus.R;
import com.fitaleks.heroesthesaurus.data.Character;
import com.fitaleks.heroesthesaurus.data.source.CharactersRepository;
import com.fitaleks.heroesthesaurus.data.source.local.CharatersLocalDataSource;
import com.fitaleks.heroesthesaurus.data.source.remote.CharactersRemoteDataSource;
import com.fitaleks.heroesthesaurus.util.ImeUtils;
import com.fitaleks.heroesthesaurus.util.TransitionUtils;
import com.fitaleks.heroesthesaurus.util.schedulers.SchedulerProvider;
import com.fitaleks.heroesthesaurus.util.transitions.CircularReveal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alexander on 02.12.16.
 */

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.searchback) ImageButton searchBack;
    @BindView(R.id.search_view) SearchView searchView;
    @BindView(R.id.search_results)
    RecyclerView recyclerView;
    CharatersListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setupSearchView();

        setupTransitions();

        adapter = new CharatersListAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onPause() {
        // needed to suppress the default window animation when closing the activity
        overridePendingTransition(0, 0);
        super.onPause();
    }

    @Override
    public void onEnterAnimationComplete() {
        // focus the search view once the enter transition finishes
        searchView.requestFocus();
        ImeUtils.showIme(searchView);
    }

    @Override
    public void onBackPressed() {
        dismiss();
    }

    protected void dismiss() {
        // clear the background else the touch ripple moves with the translation which looks bad
        searchBack.setBackground(null);
        finishAfterTransition();
    }

    private void setupSearchView() {
        searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        searchView.setImeOptions(searchView.getImeOptions() | EditorInfo.IME_ACTION_SEARCH |
                EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_FLAG_NO_FULLSCREEN);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchFor(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    clearResults();
                }
                return true;
            }
        });
    }

    private void searchFor(@NonNull final String query) {
        clearResults();
        CharactersRepository.getInstance(CharactersRemoteDataSource.getInstance(),
                CharatersLocalDataSource.getInstance(this, SchedulerProvider.getInstance()))
                .searchForCharacters(query)
                .subscribeOn(SchedulerProvider.getInstance().computation())
                .observeOn(SchedulerProvider.getInstance().ui())
                .subscribe(
                        //onNext
                        this::processTasks,
                        //onError
                        throwable -> Toast.makeText(SearchActivity.this, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show()
                        //onCompleted
                        );
    }

    private void processTasks(@NonNull List<Character> characters) {
        if (characters.isEmpty()) {
            // Show a message indicating there are no characters for that filter type.
//            processEmptyTasks();
        } else {
            // Show the list of characters
            adapter.addCharacters(characters);
            // Set the filter label's text.
//            showFilterLabel();
        }
    }

    private void clearResults() {
        adapter.clear();
    }

    private void setupTransitions() {
        // grab the position that the search icon transitions in *from*
        // & use it to configure the return transition
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onSharedElementStart(
                    List<String> sharedElementNames,
                    List<View> sharedElements,
                    List<View> sharedElementSnapshots) {
                if (sharedElements != null && !sharedElements.isEmpty()) {
                    View searchIcon = sharedElements.get(0);
                    if (searchIcon.getId() != R.id.searchback) return;
                    int centerX = (searchIcon.getLeft() + searchIcon.getRight()) / 2;
                    CircularReveal hideResults = (CircularReveal) TransitionUtils.findTransition(
                            (TransitionSet) getWindow().getReturnTransition(),
                            CircularReveal.class, R.id.results_container);
                    if (hideResults != null) {
                        hideResults.setCenter(new Point(centerX, 0));
                    }
                }
            }
        });
    }
}
