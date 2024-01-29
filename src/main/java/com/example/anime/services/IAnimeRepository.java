package com.example.anime.services;

import com.example.anime.entities.Anime;

import java.util.List;

public interface IAnimeRepository {
    List<Anime> obtenerAnimes();
}
