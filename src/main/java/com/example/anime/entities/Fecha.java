package com.example.anime.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Fecha {
    private Integer dia;
    private Integer mes;
    private Integer anio;

    public Fecha(String dateString) {
        String[] parts = dateString.split(" ");

        if(dateString.equals("?")|| dateString.isEmpty()) {//En caso de encontrar datos como este declara null en cada atributo
            this.mes=null;
            this.dia=null;
            this.anio=null;
        }else if (parts.length == 3) {//Contiene los 3 elementos de fecha
            this.mes = monthStringToNumber(parts[0]);
            this.dia = Integer.parseInt(parts[1]);
            this.anio = Integer.parseInt(parts[2]);
        } else if (parts.length == 2) {//Contiene mes y anio
            this.mes = monthStringToNumber(parts[0]);
            this.anio = Integer.parseInt(parts[1]);
        } else if (parts.length == 1) {//Contiene solo anio
            this.anio = Integer.parseInt(parts[0]);
        }
    }

    public Integer monthStringToNumber(String monthString) {//Metodo para asociar un numero a un mes
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return Arrays.asList(months).indexOf(monthString) + 1;
    }

    public String toString(){
        return dia+"-"+mes+"-"+anio;
    }
}
