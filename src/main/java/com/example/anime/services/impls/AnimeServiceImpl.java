package com.example.anime.services.impls;

import com.example.anime.entities.Anime;
import com.example.anime.services.AnimeService;
import com.example.anime.services.IAnimeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
public class AnimeServiceImpl implements AnimeService {
    private IAnimeRepository animeRepository;
    private List<Anime> animeList;

    public AnimeServiceImpl(IAnimeRepository animeRepository){//Constructor para cargar todas las entradas a una variable
        this.animeRepository = animeRepository;               //y no solicitar en cada consulta todos los datos desde
        this.animeList = animeRepository.obtenerAnimes();     //el archivo .csv
        System.out.println("Animes cargados a la lista");
    }
    @Override
    public Long contar() {
        return animeList.stream().count();
    }

    @Override
    public List<Anime> findAll() {
        return animeList.stream().collect(Collectors.toList());//Genera una lista
    }

    @Override
    public Optional<Anime> findFirst() {
        return animeList.stream().findFirst();
    }

    @Override
    public Optional<Anime> findNPosition(Integer position) {
        return animeList.stream().skip(position-1).findFirst();//Salta una posicion por el indexado en java
    }

    @Override
    public Optional<Anime> mostVoted() {
        return animeList.stream()
                .max(Comparator.comparingInt(Anime::getVote));//Compara el atributo de acuerdo al tipo de dato o semejante
    }

    @Override
    public Optional<Anime> leastVoted() {
        return animeList.stream()
                .min(Comparator.comparingInt(Anime::getVote));
    }

    @Override
    public Optional<Anime> mostPopular() {
        return animeList.stream()
                .max(Comparator.comparingInt(Anime::getPopularity));
    }

    @Override
    public Optional<Anime> leastPopular() {
        return animeList.stream()
                .min(Comparator.comparingInt(Anime::getPopularity));
    }


    @Override
    public Optional<Anime> mostEpisodes() {
        return animeList.stream()
                .filter(e-> e.getEpisodes()!=null)//Existen datos vacios y se maneja un filtro por ello
                .max(Comparator.comparingInt(Anime::getEpisodes));
    }

    @Override
    public Optional<Anime> leastEpisodes() {
        return animeList.stream()
                .filter(e-> e.getEpisodes()!=null)
                .min(Comparator.comparingInt(Anime::getEpisodes));
    }

    @Override
    public List<Anime> withoutLicensors() {
        return animeList.stream()
                .filter(e->e.getLicensors()[0].contains("None found"))//Caso donde hay una leyenda para indicar ausencia
                .collect(Collectors.toList());                        //de los datos de interes
    }

    @Override
    public List<Anime> withoutStudios() {
        return animeList.stream()
                .filter(e->e.getStudios()[0].contains("None found"))
                .collect(Collectors.toList());
    }
    @Override
    public List<Anime> episodesEven() {
        return animeList.stream()
                .filter(e -> (e.getEpisodes()!=null) && (e.getEpisodes()%2==0))//Diferente de null y par
                .collect(Collectors.toList());
    }

    @Override
    public List<Anime> episodesOdd() {
        return animeList.stream()
                .filter(e -> (e.getEpisodes()!=null) && (e.getEpisodes()%2!=0))//Diferente de null e impar
                .collect(Collectors.toList());
    }

    @Override
    public List<Anime> matchSource(String source) {
        return animeList.stream()
                .filter(e -> e.getSource().contains(source))//Que exista la cadena en el atributo
                .collect(Collectors.toList());
    }
    @Override
    public List<Anime> findByPremiered(String premiered) {
        return animeList.stream()
                .filter(e->e.getPremiered().contains(premiered))
                .collect(Collectors.toList());
    }

    @Override
    public List<Anime> findByStatus(String status) {
        return animeList.stream()
                .filter(e->e.getStatus().contains(status))
                .collect(Collectors.toList());
    }

    @Override
    public List<Anime> findByRating(String rating) {
        return animeList.stream()
                .filter(e->e.getRating().contains(rating))
                .collect(Collectors.toList());
    }

    @Override
    public Double scoreAverage() {
        return animeList.stream()
                .mapToDouble(Anime::getScore)//Convertir flujo a Double
                .average()//Obtiene el promedio de los datos convertidos a Double
                .orElse(0);//En caso de no recuperar datos devolver un cero
    }
    @Override
    public Anime findDurationMax() {
        return animeList.stream()
                .filter(e->e.getDuration()!=null)
                .max(Comparator.comparing(Anime::getDuration))
                .orElseGet(null);//En caso de no recuperar datos devolver un null
    }

    @Override
    public Anime findDurationMin() {
        return animeList.stream()
                .filter(e->e.getDuration()!=null)
                .min(Comparator.comparing(Anime::getDuration))
                .orElseGet(null);
    }

    @Override
    public List<Anime> findByScoreRange(Float min, Float max) {
        return animeList.stream()
                .filter(a->(a.getScore()>min && a.getScore()<max))
                .distinct()//Omite valores repetidos
                .collect(Collectors.toList());
    }

    @Override
    public List<Anime> findByTitle(String name) {
        return animeList.stream()
                .filter(e->e.getTitle()[0].contains(name))
                .filter(e->e.getTitle().length>1 && e.getTitle()[1].contains(name))
                .collect(Collectors.toList());
    }

    @Override
    public String[] repeatedRating() {
        Map<String, Long> RatingRepeat = animeList.stream()//Se genera un Map para capturar los Rating de cada entrada
                .filter(e->!e.getRating().equals("None"))//No deja pasar a los casos no deseados
                .collect(Collectors.groupingBy(Anime::getRating, Collectors.counting()));//Genera una agrupacion

        if (RatingRepeat.isEmpty()) {
            return null; // O cualquier otro valor por defecto si la lista está vacía
        }

        String generoMasPopular = RatingRepeat.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null); // null para cualquier otro caso que no sea la respuesta esperada
        String[] resultado = new String[]{generoMasPopular};
        return resultado;
    }

    @Override
    public String[] lessRepeatedRating() {
        Map<String, Long> RatingRepeat = animeList.stream()
                .filter(e->!e.getRating().equals("None"))
                .collect(Collectors.groupingBy(Anime::getRating, Collectors.counting()));

        if (RatingRepeat.isEmpty()) {
            return null; // O cualquier otro valor por defecto si la lista está vacía
        }

        String generoMenosPopular = RatingRepeat.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null); // null para cualquier otro caso que no sea la respuesta esperada
        String[] resultado = new String[]{generoMenosPopular};
        return resultado;
    }

    @Override
    public Anime newestAired() {
        return animeList.stream()
                .filter(anime -> anime.getAired() != null           //Se realiza un filtro por cada atributo de Aired
                        && anime.getAired()[0].getDia() != null     //que es del tipo Fecha para omitir las entradad
                        && anime.getAired()[0].getMes() != null     //que no cuenten con al menos uno de los atributos
                        && anime.getAired()[0].getAnio() != null)
                .max(Comparator.comparing(anime ->                  //Se genera la comparacion de los atributos en las entradas
                        LocalDate.of(anime.getAired()[0].getAnio(), anime.getAired()[0].getMes(), anime.getAired()[0].getDia())))
                .orElseGet(null);
    }

    @Override
    public Anime oldestAired() {
        return animeList.stream()
                .filter(anime -> anime.getAired() != null
                        && anime.getAired()[0].getDia() != null
                        && anime.getAired()[0].getMes() != null
                        && anime.getAired()[0].getAnio() != null)
                .min(Comparator.comparing(anime ->
                        LocalDate.of(anime.getAired()[0].getAnio(), anime.getAired()[0].getMes(), anime.getAired()[0].getDia())))
                .orElseGet(null);
    }

    @Override
    public String[] findAllStudios() {
        Map<String, List<String>> groupedByStudios = animeList.stream()
                .flatMap(e-> Arrays.stream(e.getStudios()))
                .collect(Collectors.groupingBy(studio -> studio));

        return groupedByStudios.keySet().toArray(new String[0]);
    }

    @Override
    public String [] findAllSources() {
        Map<String, List<Anime>> groupedByStudios = animeList.stream()
                .collect(Collectors.groupingBy(Anime::getSource));

        return groupedByStudios.keySet().toArray(new String[0]);
    }

    @Override
    public String[] findAllRating() {
        Map<String, List<Anime>> groupedByStudios = animeList.stream()
                .collect(Collectors.groupingBy(Anime::getRating));

        return groupedByStudios.keySet().toArray(new String[0]);
    }

}
