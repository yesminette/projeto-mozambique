/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ciar.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Lauro
 */
public class DataUtil {

    public static int getMes(int mes) {
        switch (mes) {
            case 1:
                return Calendar.JANUARY;
            case 2:
                return Calendar.FEBRUARY;
            case 3:
                return Calendar.MARCH;
            case 4:
                return Calendar.APRIL;
            case 5:
                return Calendar.MAY;
            case 6:
                return Calendar.JUNE;
            case 7:
                return Calendar.JULY;
            case 8:
                return Calendar.AUGUST;
            case 9:
                return Calendar.SEPTEMBER;
            case 10:
                return Calendar.OCTOBER;
            case 11:
                return Calendar.NOVEMBER;
            case 12:
                return Calendar.DECEMBER;
            default:
                return Calendar.JANUARY;
        }
    }

    public static String getHora(Date data) {
        SimpleDateFormat dateFormat =
                new SimpleDateFormat("hh:mm", new Locale("pt", "BR"));
        return dateFormat.format(data);
    }

    public static String getData(Date data) {
        SimpleDateFormat dateFormat =
                new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
        return dateFormat.format(data);
    }

    public static String calculaTempoDecorrido(Date horarioOriginal) {
        String unidade = "minutos";
        long numero = 0;
        final long MINUTOS = 60000;
        final long HORAS = 60 * MINUTOS;
        final long DIAS = 24 * HORAS;
        final long MESES = 30 * DIAS;
        final long ANOS = 12 * MESES;
        numero = new Date().getTime() - horarioOriginal.getTime();
        long tempoDecorrido = 0;

        if (numero > MINUTOS) {
            tempoDecorrido = numero / MINUTOS;
            unidade = (tempoDecorrido > 1) ? "minutos" : "minuto";
            if (numero > HORAS) {
                tempoDecorrido = numero / HORAS;
                unidade = (tempoDecorrido > 1) ? "horas" : "hora";
                if (numero > DIAS) {
                    tempoDecorrido = numero / DIAS;
                    unidade = (tempoDecorrido > 1) ? "dias" : "dia";
                    if (numero > MESES) {
                        tempoDecorrido = numero / MESES;
                        unidade = (tempoDecorrido > 1) ? "meses" : "mês";
                        if (numero > ANOS) {
                            tempoDecorrido = numero / ANOS;
                            unidade = (tempoDecorrido > 1) ? "anos" : "ano";
                        }
                    }
                }
            }
        } else {
            tempoDecorrido = 0;
            unidade = "minutos";
        }

        String tempo = tempoDecorrido + " " + unidade + " atrás";
        
        return tempo;
    }
}
