package com.fitaleks.heroesthesaurus;

import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitaleks.heroesthesaurus.data.Character;
import com.fitaleks.heroesthesaurus.database.CharactersProvider;
import com.fitaleks.heroesthesaurus.network.NetworkHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;
import rx.schedulers.Schedulers;

public class MainActivityFragment extends Fragment {
    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private CharatersListAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean loading     = true;
    private int previousTotal   = 0;

    private Observable<List<Character>> mListObservable;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mListObservable = getObservable();
    }

    private Observable<List<Character>> getObservable() {
        final long curTime = System.currentTimeMillis();
        return NetworkHelper.getRestAdapter()
                //TODO: pass correct number of already loaded characters
                .getCharacters("name", curTime, NetworkHelper.getHash(curTime), 0)
                .timeout(15, TimeUnit.SECONDS)
                .retry(3)
                .onErrorResumeNext(Observable.<List<Character>>empty())
                .doOnNext(this::saveData)
//                .flatMap(Observable::from)
//                .doOnNext(Character::saveModel)
//                .toSortedList()
//                .distinct()
//                .cache()
                .subscribeOn(Schedulers.io());
    }

    private void saveData(final List<Character> chars) {
        ArrayList<ContentProviderOperation> batchOperation = new ArrayList<>(chars.size());
        for (final Character character : chars) {
            batchOperation.add(character.saveModel());
        }
        try {
            getActivity().getContentResolver().applyBatch(CharactersProvider.AUTHORITY, batchOperation);
        } catch (RemoteException | OperationApplicationException e) {
            Log.e(LOG_TAG, "Error applying batch insert", e);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        mAdapter = new CharatersListAdapter(new ArrayList<>());
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.addOnScrollListener(listen);

        recyclerView.setAdapter(mAdapter);

        ViewObservable.bindView(recyclerView, mListObservable)
                .subscribe(this::updateAdapter);
        return rootView;
    }

    int firstVisibleItem, visibleItemCount, totalItemCount;
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

                getObservable().observeOn(AndroidSchedulers.mainThread())
                        .subscribe(MainActivityFragment.this::updateAdapter);

                loading = true;
            }
        }
    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void updateAdapter(List<Character> listOfCharacters) {
        this.mAdapter.addCharacters(listOfCharacters);
    }

    @Override
    public void onDestroyView() {
        recyclerView.removeOnScrollListener(listen);
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
