package com.gmail.smaglenko.rickandmortyapp.service;

import com.gmail.smaglenko.rickandmortyapp.model.MovieCharacter;
import java.util.List;

public interface MovieCharacterService {
    void syncExternalCharacters();

    MovieCharacter getRandomCharacter();

    List<MovieCharacter> findAllByNameContains(String namePart);
}
