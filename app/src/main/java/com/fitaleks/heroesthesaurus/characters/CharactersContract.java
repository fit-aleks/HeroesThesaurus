package com.fitaleks.heroesthesaurus.characters;

import com.fitaleks.heroesthesaurus.BasePresenter;
import com.fitaleks.heroesthesaurus.BaseView;
import com.fitaleks.heroesthesaurus.data.Character;

import java.util.List;

/**
 * Created by Alexander on 01.12.16.
 */

public interface CharactersContract {
    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);

        void showCharacters(List<Character> characters);
        void showLoadingError();
    }

    interface Presenter extends BasePresenter {
        void loadCharacters(boolean forceUpdate);
    }
}
