package com.fitaleks.heroesthesaurus.characters;

import android.support.annotation.NonNull;

import com.fitaleks.heroesthesaurus.data.Character;
import com.fitaleks.heroesthesaurus.data.source.CharactersRepository;
import com.fitaleks.heroesthesaurus.util.schedulers.BaseSchedulerProvider;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Alexander on 01.12.16.
 */

public class CharactersPresenter implements CharactersContract.Presenter {

    @NonNull
    private final CharactersRepository charactersRepository;

    @NonNull
    private final CharactersContract.View charactersView;

    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;

    private boolean mFirstLoad = true;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public CharactersPresenter(@NonNull CharactersRepository charactersRepository,
                               @NonNull CharactersContract.View charactersView,
                               @NonNull BaseSchedulerProvider mSchedulerProvider) {
        this.charactersRepository = charactersRepository;
        this.charactersView = charactersView;
        this.mSchedulerProvider = mSchedulerProvider;

        mSubscriptions = new CompositeSubscription();
        charactersView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadCharacters(false);
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.unsubscribe();
    }

    @Override
    public void loadCharacters(boolean forceUpdate) {
        loadCharacters(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadCharacters(final boolean forceUpdate, final boolean showLoadingUi) {
        if (showLoadingUi) {
            charactersView.setLoadingIndicator(true);
        }
        if (forceUpdate) {
            charactersRepository.refreshCharacters();
        }
        mSubscriptions.clear();

        Subscription subscription = charactersRepository.getCharacters()
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(
                        //onNext
                        this::processTasks,
                        //onError
                        throwable -> charactersView.showLoadingError(),
                        //onCompleted
                        () -> charactersView.setLoadingIndicator(false));
        mSubscriptions.add(subscription);
    }

    private void processTasks(@NonNull List<Character> tasks) {
        if (tasks.isEmpty()) {
            // Show a message indicating there are no tasks for that filter type.
//            processEmptyTasks();
        } else {
            // Show the list of tasks
            charactersView.showCharacters(tasks);
            // Set the filter label's text.
//            showFilterLabel();
        }
    }
}
