package com.fitaleks.heroesthesaurus.characters;

import android.app.SharedElementCallback;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.transition.TransitionSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.fitaleks.heroesthesaurus.R;
import com.fitaleks.heroesthesaurus.util.ImeUtils;
import com.fitaleks.heroesthesaurus.util.TransitionUtils;
import com.fitaleks.heroesthesaurus.util.transitions.CircularReveal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alexander on 02.12.16.
 */

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.searchback) ImageButton searchBack;
    @BindView(R.id.search_view) SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setupSearchView();

        setupTransitions();
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
