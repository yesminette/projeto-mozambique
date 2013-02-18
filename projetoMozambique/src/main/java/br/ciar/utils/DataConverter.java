package br.ciar.utils;

import br.ciar.utils.DataConverter.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lauro
 */
public class DataConverter {

    public static String converterData(Date data, Data dataType) {
        String dataFormatada = "";
        SimpleDateFormat dateFormat =
                new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
        switch (dataType) {
            case DDMMAAAA:
                dateFormat =
                        new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));

                break;
            case PARARSS:
                dateFormat =
                        new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss a", new Locale("pt", "BR"));
                break;
            case DDMMAAAAHHMM:
                dateFormat =
                        new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("pt", "BR"));
                break;
            case DDMMAAAAHHMMSS:
                dateFormat =
                        new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", new Locale("pt", "BR"));
                break;
            case HHMM:
                dateFormat =
                        new SimpleDateFormat("HH:mm", new Locale("pt", "BR"));
                break;
            default:
                dateFormat =
                        new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
        }
        dataFormatada = dateFormat.format(data);
        return dataFormatada;

    }

    public static Date converterData(String data, Data dataType) {
        Date dataFormatada = null;
        SimpleDateFormat dateFormat =
                new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
        switch (dataType) {
            case DDMMAAAA:
                dateFormat =
                        new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));

                break;
            case PARARSS:
                dateFormat =
                        new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss a", new Locale("pt", "BR"));
                break;
            case DDMMAAAAHHMM:
                dateFormat =
                        new SimpleDateFormat("dd/MM/yyyy hh:mm", new Locale("pt", "BR"));
                break;
            case DDMMAAAAHHMMSS:
                dateFormat =
                        new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", new Locale("pt", "BR"));
                break;
            case HHMM:
                dateFormat =
                        new SimpleDateFormat("hh:mm", new Locale("pt", "BR"));
                break;
            default:
                dateFormat =
                        new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
        }
        try {
            dataFormatada = dateFormat.parse(data);
        } catch (Exception ex) {
            dataFormatada = new Date();
        }
        return dataFormatada;

    }

    public enum Data {

        DDMMAAAA, PARARSS, DDMMAAAAHHMM, DDMMAAAAHHMMSS, HHMM;
    }
}
