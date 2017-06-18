package com.fitaleks.heroesthesaurus.ui

import com.fitaleks.heroesthesaurus.BasePresenter
import com.fitaleks.heroesthesaurus.BaseView
import com.fitaleks.heroesthesaurus.data.MarvelCharacter

/**
 * Created by Alexander on 01.12.16.
 */
interface CharactersContract {
    interface View : BaseView<Presenter> {
        fun setLoadingIndicator(active: Boolean)

        fun showCharacters(characters: List<MarvelCharacter>)
        fun showLoadingError()
    }

    interface Presenter : BasePresenter {
        fun loadCharacters(forceUpdate: Boolean)
    }
}
