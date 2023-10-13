package com.gmail.smaglenko.rickandmortyapp.dto.mapper;

import com.gmail.smaglenko.rickandmortyapp.dto.external.ApiCharacterDto;
import com.gmail.smaglenko.rickandmortyapp.dto.response.CharacterResponseDto;
import com.gmail.smaglenko.rickandmortyapp.model.Gender;
import com.gmail.smaglenko.rickandmortyapp.model.MovieCharacter;
import com.gmail.smaglenko.rickandmortyapp.model.Status;
import org.springframework.stereotype.Component;

@Component
public class MovieCharacterMapper {
    public MovieCharacter parseApiCharacterResponseDto(ApiCharacterDto dto) {
        MovieCharacter movieCharacter = new MovieCharacter();
        movieCharacter.setExternalId(dto.getId());
        movieCharacter.setName(dto.getName());
        movieCharacter.setGender(Gender.valueOf(dto.getGender()));
        movieCharacter.setStatus(Status.valueOf(dto.getStatus()));
        return movieCharacter;
    }

    public CharacterResponseDto toResponseDto(MovieCharacter movieCharacter) {
        CharacterResponseDto responseDto = new CharacterResponseDto();
        responseDto.setId(movieCharacter.getId());
        responseDto.setExternalId(movieCharacter.getExternalId());
        responseDto.setName(movieCharacter.getName());
        responseDto.setGender(movieCharacter.getGender());
        responseDto.setStatus(movieCharacter.getStatus());
        return responseDto;
    }
}
