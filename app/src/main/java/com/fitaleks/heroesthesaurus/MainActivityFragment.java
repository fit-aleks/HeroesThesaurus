package com.fitaleks.heroesthesaurus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitaleks.heroesthesaurus.data.Character;
import com.fitaleks.heroesthesaurus.network.NetworkHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.view.ViewObservable;
import rx.schedulers.Schedulers;

public class MainActivityFragment extends Fragment {

    private CharatersListAdapter mAdapter;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private Observable<List<Character>> mListObservable;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final long curTime = System.currentTimeMillis();
        mListObservable = NetworkHelper.getRestAdapter()
                .getCharacters(NetworkHelper.MARVEL_PUBLIC_KEY, "name", curTime, NetworkHelper.getHash(curTime))
                .timeout(15, TimeUnit.SECONDS)
                .retry(3)
                .onErrorResumeNext(Observable.<List<Character>>empty())
                .flatMap(Observable::from)
                .doOnNext(Character::saveModel)
                .toSortedList()
                .cache()
                .subscribeOn(Schedulers.io());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        mAdapter = new CharatersListAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);

        ViewObservable.bindView(recyclerView, mListObservable).subscribe(this::updateAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void updateAdapter(List<Character> listOfCharacters) {
        this.mAdapter.update(listOfCharacters);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
