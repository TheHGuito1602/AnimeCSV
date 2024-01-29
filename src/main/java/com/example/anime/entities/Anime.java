package com.example.anime.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Arrays;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Anime {
    private String [] title;
    private Float score;
    private Integer vote;
    private Integer ranked;
    private Integer popularity;
    private Integer episodes;
    private String status;
    private Fecha[] aired;
    private String premiered;
    private String [] producers;
    private String [] licensors;
    private String [] studios;
    private String source;
    private Duration duration;
    private String rating;

    public String toString(){
        return Arrays.toString(title) + ", " + score + ", "+ vote + "," + ranked + "," + popularity + "," + episodes + "," +
                status + "," + Arrays.toString(aired) + "," + premiered + "," + Arrays.toString(producers) + "," + Arrays.toString(licensors) + "," +
                Arrays.toString(studios) + "," + source + "," + duration + "," + rating;
    }
}
