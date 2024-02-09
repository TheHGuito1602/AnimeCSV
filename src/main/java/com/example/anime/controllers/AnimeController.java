package com.example.anime.controllers;

import com.example.anime.entities.Anime;
import com.example.anime.services.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@Log4j2
@RestController
@RequestMapping("/api/animes")
@RequiredArgsConstructor
public class AnimeController {
    private final AnimeService animeServices;

    @GetMapping("/count")
    public ResponseEntity<Long> countAll(){
        return Optional
                .of(animeServices.contar())//Se llama al metodo correspondiente
                .map(ResponseEntity::ok)//Respuesta HTTP 200
                .orElseGet(ResponseEntity.notFound()::build);//Respuesta HTTP 400
    }

    @GetMapping
    public ResponseEntity<List<Anime>> findAll(){
        return Optional
                .of(animeServices.findAll())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/find-title/{title}")//Elemento entre {} es el parametro ingresado en la URI a capturar
    public ResponseEntity<List<Anime>> findByTitle(@PathVariable("title") String title){//Manejo de variable de la URI a parametro de metodo de un Controller
        return Optional
                .of(animeServices.findByTitle(title))
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/find-premiered/{premiered}")
    public ResponseEntity<List<Anime>> findByPremiered(@PathVariable("premiered") String premiered){
        return Optional
                .of(animeServices.findByPremiered(premiered))
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/find-status/{status}")
    public ResponseEntity<List<Anime>> findByStatus(@PathVariable("status") String status){
        return Optional
                .of(animeServices.findByStatus(status))
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/find-rating/{rating}")
    public ResponseEntity<List<Anime>> findByRating(@PathVariable("rating") String rating){
        return Optional
                .of(animeServices.findByRating(rating))
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/first")
    public ResponseEntity<Optional<Anime>> findFirst(){
        return Optional
                .of(animeServices.findFirst())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/find-position/{position}")
    public ResponseEntity<Optional<Anime>> findByPosition(@PathVariable("position") Integer position){
        return Optional
                .of(animeServices.findNPosition(position))
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/range-score/{min}/{max}")
    public ResponseEntity<List<Anime>> findBetweenScoreRange(@PathVariable("min") Float min, @PathVariable("max") Float max){
        return Optional
                .of(animeServices.findByScoreRange(min,max))
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/average/score")
    public ResponseEntity<Double> getAverageScore(){
        return Optional
                .of(animeServices.scoreAverage())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/most/voted")
    public ResponseEntity<Optional<Anime>> findMostVoted(){
        return Optional
                .of(animeServices.mostVoted())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/least/voted")
    public ResponseEntity<Optional<Anime>> findLeastVoted(){
        return Optional
                .of(animeServices.leastVoted())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/most/popular")
    public ResponseEntity<Optional<Anime>> findMostPopular(){
        return Optional
                .of(animeServices.mostPopular())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/least/popular")
    public ResponseEntity<Optional<Anime>> findLeastPopular(){
        return Optional
                .of(animeServices.leastPopular())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/most/episodes")
    public ResponseEntity<Optional<Anime>> findMostEpisodes(){
        return Optional
                .of(animeServices.mostEpisodes())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/least/episodes")
    public ResponseEntity<Optional<Anime>> findLeastEpisodes(){
        return Optional
                .of(animeServices.leastEpisodes())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/episodes/even")
    public ResponseEntity<List<Anime>> findEpisodesEven(){
        return Optional
                .of(animeServices.episodesEven())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/episodes/odd")
    public ResponseEntity<List<Anime>> findEpisodesOdd(){
        return Optional
                .of(animeServices.episodesOdd())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/source/{source}")
    public ResponseEntity<List<Anime>> findBySource(@PathVariable("source") String source){
        return Optional
                .of(animeServices.matchSource(source))
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/most/repeat/rating")
    public ResponseEntity<String[]> mostRepeatRating(){
        return Optional
                .of(animeServices.repeatedRating())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/least/repeat/rating")
    public ResponseEntity<String[]> leastRepeatRating(){
        return Optional
                .of(animeServices.lessRepeatedRating())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/duration/max")
    public ResponseEntity<Anime> findDurationMax(){
        return Optional
                .of(animeServices.findDurationMax())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/duration/min")
    public ResponseEntity<Anime> findDurationMin(){
        return Optional
                .of(animeServices.findDurationMin())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/aired/newest")
    public ResponseEntity<Anime> findNewestAired(){
        return Optional
                .of(animeServices.newestAired())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/aired/oldest")
    public ResponseEntity<Anime> findOldestAired(){
        return Optional
                .of(animeServices.oldestAired())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/studios/all")
    public ResponseEntity<String[]> findAllStudios(){
        return Optional
                .of(animeServices.findAllStudios())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/sources/all")
    public ResponseEntity<String[]> findAllSources(){
        return Optional
                .of(animeServices.findAllSources())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/rating/all")
    public ResponseEntity<String[]> findAllrating(){
        return Optional
                .of(animeServices.findAllRating())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/licensors/without")
    public ResponseEntity<List<Anime>> findWithoutLicensors(){
        return Optional
                .of(animeServices.withoutLicensors())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/studios/without")
    public ResponseEntity<List<Anime>> findWithoutStudios(){
        return Optional
                .of(animeServices.withoutStudios())
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }
}
