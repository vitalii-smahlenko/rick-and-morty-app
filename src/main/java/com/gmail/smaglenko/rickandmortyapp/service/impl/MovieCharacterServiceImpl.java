package com.gmail.smaglenko.rickandmortyapp.service.impl;

import com.gmail.smaglenko.rickandmortyapp.dto.external.ApiCharacterDto;
import com.gmail.smaglenko.rickandmortyapp.dto.external.ApiResponseDto;
import com.gmail.smaglenko.rickandmortyapp.dto.mapper.MovieCharacterMapper;
import com.gmail.smaglenko.rickandmortyapp.model.MovieCharacter;
import com.gmail.smaglenko.rickandmortyapp.repository.MovieCharacterRepository;
import com.gmail.smaglenko.rickandmortyapp.service.HttpClient;
import com.gmail.smaglenko.rickandmortyapp.service.MovieCharacterService;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MovieCharacterServiceImpl implements MovieCharacterService {
    private final HttpClient httpClient;
    private final MovieCharacterRepository movieCharacterRepository;
    private final MovieCharacterMapper mapper;

    @Autowired
    public MovieCharacterServiceImpl(HttpClient httpClient, MovieCharacterRepository movieCharacterRepository, MovieCharacterMapper mapper) {
        this.httpClient = httpClient;
        this.movieCharacterRepository = movieCharacterRepository;
        this.mapper = mapper;
    }

    @PostConstruct
    @Scheduled(cron = "0 8 * * * ?")
    @Override
    public void syncExternalCharacters() {
        ApiResponseDto apiResponseDto
                = httpClient.get("https://rickandmortyapi.com/api/charecter",
                ApiResponseDto.class);
        saveDtosToDb(apiResponseDto);
        while (apiResponseDto.getInfo().getNext() != null) {
            apiResponseDto = httpClient.get("https://rickandmortyapi.com/api/charecter",
                    ApiResponseDto.class);
            saveDtosToDb(apiResponseDto);
        }
    }

    @Override
    public MovieCharacter getRandomCharacter() {
        long count = movieCharacterRepository.count();
        long randomId = (long) (Math.random() + count);
        return movieCharacterRepository.getById(randomId);
    }

    @Override
    public List<MovieCharacter> findAllByNameContains(String namePart) {
        return movieCharacterRepository.findAllByNameContains(namePart);
    }

    private void saveDtosToDb(ApiResponseDto responseDto) {
        Map<Long, ApiCharacterDto> extendsDtos = Arrays.stream(responseDto.getResults())
                .collect(Collectors.toMap(ApiCharacterDto::getId, Function.identity()));
        Set<Long> externalIds = extendsDtos.keySet();
        List<MovieCharacter> existingCharacters = movieCharacterRepository.findAllByExternalIdIn(externalIds);
        Map<Long, MovieCharacter> existingCharactersWithIds = existingCharacters.stream()
                .collect(Collectors.toMap(MovieCharacter::getExternalId, Function.identity()));
        Set<Long> existingIds = existingCharactersWithIds.keySet();
        externalIds.removeAll(existingIds);
        List<MovieCharacter> characterToSave = externalIds.stream()
                .map(i -> mapper.parseApiCharacterResponseDto(extendsDtos.get(i)))
                .toList();
        movieCharacterRepository.saveAll(characterToSave);
    }
}
