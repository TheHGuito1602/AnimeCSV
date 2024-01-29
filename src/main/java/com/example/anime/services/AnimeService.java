package com.example.anime.services;


import com.example.anime.entities.Anime;

import java.util.List;
import java.util.Optional;

public interface AnimeService {
    Long contar();
    List<Anime> findAll();
    Optional<Anime> findFirst();
    Optional<Anime> findNPosition(Integer position);
    Optional<Anime> mostVoted();
    Optional<Anime> leastVoted();
    Optional<Anime> mostPopular();
    Optional<Anime> leastPopular();
    Optional<Anime> mostEpisodes();
    Optional<Anime> leastEpisodes();
    List<Anime> withoutLicensors();
    List<Anime> withoutStudios();
    List<Anime> episodesEven();
    List<Anime> episodesOdd();
    List<Anime> matchSource(String source);
    List<Anime> findByPremiered(String premiered);
    List<Anime> findByStatus(String status);
    List<Anime> findByRating(String rating);
    Double scoreAverage();
    Anime findDurationMax();
    Anime findDurationMin();
    List<Anime> findByScoreRange(Float min, Float max);
    List<Anime> findByTitle(String name);
    String repeatedRating();
    String lessRepeatedRating();
    Anime newestAired();
    Anime oldestAired();
    String [] findAllStudios();
    String [] findAllSources();
    String []  findAllRating();
}
