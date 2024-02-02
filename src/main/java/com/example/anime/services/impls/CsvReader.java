package com.example.anime.services.impls;

import com.example.anime.entities.Anime;
import com.example.anime.entities.Fecha;
import com.example.anime.services.IAnimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CsvReader implements IAnimeRepository {

    private static final String SEPARADOR = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";//Expresion regular para evitar caracteres que dificulte la lectura del .csv
    private final String RUTA = "C:/Users/eljug/OneDrive/Documentos/UNPA/SEMESTRE 7/Programación Funcional/ProyectoFinal/API/anime/Archivos/anime.csv";

    @Override
    public List<Anime> obtenerAnimes() {
        Path path = Path.of(RUTA); //Carga la ruta en una variable de tipo PATH

        try {
            return Files.lines(path)//Lectura linea a linea del archivo
                    .skip(1)//Se salta la primera posicion por ser la cabecera
                    .map(linea -> linea.split(SEPARADOR))//Aplica el separador para cada columna
                    .map(this::mapearAnime)//Se genera un mapeo para transformar a objeto
                    .collect(Collectors.toList());//Genera una lista

        } catch (IOException e) {
            // Manejar la excepción apropiadamente
            e.printStackTrace();
            System.out.println("Algún atributo falta o no coincide con su tipo de dato");
            return Collections.emptyList(); // o lanza una excepción personalizada o retorna un valor predeterminado
        }

    }

    Anime mapearAnime(String[] campos) {
        String[] title = campos[0] //Por medio de una expresion regular se separan los titulos en su versiones
                .split("(?<=\\S)(?=[A-Z](?![A-Za-z]*(\\)|\\d)))((?<![:;\\- ])\\s*)|(?<=[A-Za-z])(?=[A-Z][a-z])");
        Float score = (!campos[1].isEmpty()) ? Float.parseFloat(campos[1]) : null;//Expresion ternaria para manejo de datos
        Integer vote = (!campos[2].isEmpty()) ? Integer.parseInt(campos[2]) : null;
        Integer ranked = (!campos[3].isEmpty()) ? Integer.parseInt(campos[3]) : null;
        Integer popularity = (!campos[4].isEmpty()) ? Integer.parseInt(campos[4]) : null;
        Integer episodes = (campos[5] == null || campos[5].isEmpty() || campos[5].equalsIgnoreCase("unknown")) ? null
                : Integer.parseInt(campos[5]);
        String status = campos[6];
        Fecha[] aired = obtenerFecha(campos[7].replaceAll("\"", ""));
        String premiered = campos[8];
        String[] producers = campos[9].replaceAll("[\\[\\]\"]", "").split(",");
        String[] licensors = campos[10].replaceAll("\"", "").replaceAll("      ", "").split(", ");
        String[] studios = campos[11].replaceAll("\"", "").replaceAll("      ", "").split(", ");
        String source = campos[12];
        Duration duration = parseDuration(campos[13]);
        String raiting = campos[14];

        return new Anime(title, score, vote, ranked, popularity, episodes, status,
                aired, premiered, producers, licensors, studios, source,
                duration, raiting);
    }

    private Fecha[] obtenerFecha(String fecha){
        String[] fechaN = fecha.split(" to ");//Separa elementos por la expresion dada
        Fecha[] fechas = new Fecha[2];//Inicializa la fecha inicio y fin

        fechas[0] = (fechaN[0] != "") ? parseFecha(fechaN[0].replaceAll(",","")) : null;
        fechas[1] = (fechaN.length>1 && fechaN[1] != "") ? parseFecha(fechaN[1].replaceAll(",", "")) : null;
        return fechas;
    }

    private Fecha parseFecha(String fecha){
        return new Fecha(fecha);
    }//Genera la fecha
    public Duration parseDuration(String duration) {
        Pattern pattern = Pattern.compile("(?:(\\d+)\\s*hr\\.?)?\\s*(?:(\\d+)\\s*min\\.?)?");//Captura horas y minutos
        Matcher matcher = pattern.matcher(duration);//Hace match para capturar los datos de hora y fecha

        if (matcher.find()) {//Condicion para encontrar datos
            int hours = matcher.group(1) != null ? Integer.parseInt(matcher.group(1)) : 0;
            int minutes = matcher.group(2) != null ? Integer.parseInt(matcher.group(2)) : 0;

            if (hours > 0 || minutes > 0) {//Sino existe alguno se retorna lo capturado
                return Duration.ofHours(hours).plusMinutes(minutes);
            }
        }

        return null;
    }

}
