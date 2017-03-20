package com.fitaleks.heroesthesaurus.characters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fitaleks.heroesthesaurus.R;
import com.fitaleks.heroesthesaurus.data.Character;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.fitaleks.heroesthesaurus.util.Utils.checkNotNull;

public class MainActivityFragment extends Fragment implements CharactersContract.View {
    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private CharatersListAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean loading     = true;
    private int previousTotal   = 0;

    private CharactersContract.Presenter presenter;
    private Unbinder unbinder;

    public static MainActivityFragment newInstance() {
        return new MainActivityFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, rootView);
//        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mAdapter = new CharatersListAdapter(new ArrayList<>());
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.addOnScrollListener(listen);

        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private final RecyclerView.OnScrollListener listen = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = mLinearLayoutManager.getItemCount();
            firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            if (!loading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + 2)) {
                // End has been reached

//                getObservable().observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(MainActivityFragment.this::updateAdapter);

                loading = true;
            }
        }
    };

    @Override
    public void onDestroyView() {
        recyclerView.removeOnScrollListener(listen);
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showCharacters(List<Character> characters) {
        mAdapter.addCharacters(characters);
    }

    @Override
    public void showLoadingError() {
        Toast.makeText(getContext(), "loading error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(CharactersContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }
}
