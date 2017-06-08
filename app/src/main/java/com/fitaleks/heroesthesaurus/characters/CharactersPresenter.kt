package com.fitaleks.heroesthesaurus.characters

import com.fitaleks.heroesthesaurus.data.MarvelCharacter
import com.fitaleks.heroesthesaurus.data.source.CharactersRepository

/**
 * Created by Alexander on 01.12.16.
 */
class CharactersPresenter(private val charactersRepository: CharactersRepository,
                          private val charactersView: CharactersContract.View) : CharactersContract.Presenter {

    private var mFirstLoad = true

//    private val mSubscriptions = CompositeSubscription()

    init {
        charactersView.setPresenter(this)
    }

    override fun subscribe() {
        loadCharacters(false)
    }

    override fun unsubscribe() {
//        mSubscriptions.unsubscribe()
    }

    override fun loadCharacters(forceUpdate: Boolean) {
//        loadCharacters(forceUpdate || mFirstLoad, true)
        mFirstLoad = false
    }

//    private fun loadCharacters(forceUpdate: Boolean, showLoadingUi: Boolean) {
//        if (showLoadingUi) {
//            charactersView.setLoadingIndicator(true)
//        }
//        if (forceUpdate) {
//            charactersRepository.refreshCharacters()
//        }
//        mSubscriptions.clear()
//
//        val subscription = charactersRepository.getCharacters()
//                .subscribeOn(mSchedulerProvider.computation())
//                .observeOn(mSchedulerProvider.ui())
//                .subscribe(
//                        //onNext
//                        { this.processTasks(it) },
//                        //onError
//                        { _ -> charactersView.showLoadingError() },
//                        //onCompleted
//                        { charactersView.setLoadingIndicator(false) }
//                )
//        mSubscriptions.add(subscription)
//    }

    private fun processTasks(tasks: List<MarvelCharacter>) {
        if (tasks.isEmpty()) {
            // Show a message indicating there are no tasks for that filter type.
            //            processEmptyTasks();
        } else {
            // Show the list of tasks
            charactersView.showCharacters(tasks)
            // Set the filter label's text.
            //            showFilterLabel();
        }
    }
}
