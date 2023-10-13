package com.gmail.smaglenko.rickandmortyapp.dto.response;

import com.gmail.smaglenko.rickandmortyapp.model.Gender;
import com.gmail.smaglenko.rickandmortyapp.model.Status;
import lombok.Data;

@Data
public class CharacterResponseDto {
    private Long id;
    private Long externalId;
    private String name;
    private Status status;
    private Gender gender;
}
