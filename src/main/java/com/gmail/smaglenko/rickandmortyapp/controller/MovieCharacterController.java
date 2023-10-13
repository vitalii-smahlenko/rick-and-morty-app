package com.gmail.smaglenko.rickandmortyapp.controller;

import com.gmail.smaglenko.rickandmortyapp.dto.mapper.MovieCharacterMapper;
import com.gmail.smaglenko.rickandmortyapp.dto.response.CharacterResponseDto;
import com.gmail.smaglenko.rickandmortyapp.model.MovieCharacter;
import com.gmail.smaglenko.rickandmortyapp.service.MovieCharacterService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie-character")
public class MovieCharacterController {
    private final MovieCharacterService characterService;
    private final MovieCharacterMapper mapper;

    public MovieCharacterController(MovieCharacterService characterService,
                                    MovieCharacterMapper mapper) {
        this.characterService = characterService;
        this.mapper = mapper;
    }

    @GetMapping("/random")
    public CharacterResponseDto getRandom() {
        MovieCharacter character = characterService.getRandomCharacter();
        return mapper.toResponseDto(character);
    }

    @GetMapping("/by-name")
    public List<CharacterResponseDto> getByName(@RequestParam("namePart") String namePart) {
        return characterService.findAllByNameContains(namePart).stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
