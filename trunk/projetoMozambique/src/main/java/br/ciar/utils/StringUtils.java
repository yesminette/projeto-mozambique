package br.ciar.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

public class StringUtils {

    static String[] conjuncoesArray = {"di", "de", "da", "do", "dos", "das", "a", "o", "e", ")", "(", ",", "\"", "\'", "\\", "/", "em", "no", "na", "nos", "nas"};

    public static String primeriaMaiuscula(String palavra) {
        if (palavra != null && !palavra.equalsIgnoreCase("")) {
            int len = palavra.length();
            String out = "";
            out += palavra.substring(0, 1).toUpperCase();
            out += palavra.substring(1, len);
            return out;
        }
        return palavra;
    }

    public static String truncaLength(String aTruncar, int maxLength) {
        if (aTruncar.length() > maxLength) {
            return aTruncar.substring(0, maxLength);
        } else {
            return aTruncar;
        }
    }

    public static String trataCasaDecimal(Double valor) {
        String tx = valor.toString();
        tx = tx.replace('.', ',');
        if (tx.contains(",")) {
            if (tx.split(",")[1].length() == 1) {
                tx += "0";
            } else if (tx.split(",")[1].length() == 0) {
                tx += "00";
            }
        }
        tx = tx.replace(',', '.');
        return tx;
    }

    public static String[] removePreposicoes(String[] str) {

        ArrayList<String> conjuncoes = toArrayList(conjuncoesArray);
        ArrayList<String> palavras = toArrayList(str);

        for (String conjuncao : conjuncoes) {
            if (palavras.contains(conjuncao)) {
                palavras.remove(conjuncao);
            }
        }

        return toArray(palavras);
    }

    public static String[] minusculo(String[] str) {
        for (int i = 0; i < str.length; i++) {
            str[i] = str[i].toLowerCase();

        }
        return str;
    }

    public static String[] sinonimos(String[] str) {

        //caso excepcional
        for (int i = 0; i < str.length; i++) {
            if (str[i].equals("loirao") || str[i].equals("loirão") || str[i].equals("loiraos") || str[i].equals("loirãos")
                    || str[i].equals("loiroe") || str[i].equals("loirõe") || str[i].equals("loiroes") || str[i].equals("loirões")) {
                str[i] = "loira";
            }
        }

        //sufixos
        String[] sufixosEleArray = {"ao", "aos", "ão", "ãos", "oe", "oes", "õe", "ões", "azão", "azao", "os", "aço", "aços", "aco", "acos", "íssimo", "issimo",
            "íssimos", "issimos", "inho", "inhos", ",", "(", ")"};

        String[] sufixosElaArray = {"azona", "azõnas", "as", "ona", "onas", "onas", "aça", "aças", "aca", "acas",
            "íssima", "issima", "íssimas", "issimas", "inha", "inhas",
            "ao", "aos", "ão", "ãos", "oe", "oes", "õe", "ões", ",", "(", ")"};

        //sinonimos
        HashMap<String, String> racasElePrefix = new HashMap<String, String>();
        racasElePrefix.put("loir", "loiro");
        racasElePrefix.put("branc", "loiro");
        racasElePrefix.put("clar", "loiro");
        racasElePrefix.put("moren", "moreno");
        racasElePrefix.put("pard", "moreno");
        racasElePrefix.put("mulat", "moreno");
        racasElePrefix.put("negr", "negro");
        racasElePrefix.put("pret", "negro");
        racasElePrefix.put("sarar", "negro");
        racasElePrefix.put("cabocl", "negro");
        racasElePrefix.put("escur", "negro");
        racasElePrefix.put("criol", "negro");
        racasElePrefix.put("crioul", "negro");
        racasElePrefix.put("ruiv", "ruivo");
        racasElePrefix.put("verm", "ruivo");
        racasElePrefix.put("asiátic", "asiático");
        racasElePrefix.put("chines", "asiático");
        racasElePrefix.put("japones", "asiático");
        racasElePrefix.put("korean", "asiático");
        racasElePrefix.put("vietnamit", "asiático");
        racasElePrefix.put("amarel", "asiático");

        HashMap<String, String> racasElaPrefix = new HashMap<String, String>();
        racasElaPrefix.put("loir", "loira");
        racasElaPrefix.put("branc", "loira");
        racasElaPrefix.put("clar", "loira");
        racasElaPrefix.put("moren", "morena");
        racasElaPrefix.put("pard", "morena");
        racasElaPrefix.put("mulat", "morena");
        racasElaPrefix.put("negr", "negra");
        racasElaPrefix.put("pret", "negra");
        racasElaPrefix.put("sarar", "negra");
        racasElaPrefix.put("cabocl", "negra");
        racasElaPrefix.put("escur", "negra");
        racasElaPrefix.put("criol", "negra");
        racasElaPrefix.put("crioul", "negra");
        racasElaPrefix.put("ruiv", "ruiva");
        racasElaPrefix.put("verm", "ruiva");
        racasElaPrefix.put("asiátic", "asiática");
        racasElaPrefix.put("chines", "asiática");
        racasElaPrefix.put("japones", "asiática");
        racasElaPrefix.put("korean", "asiática");
        racasElaPrefix.put("vietnamit", "asiática");
        racasElaPrefix.put("amarel", "asiática");



        for (int i = 0; i < str.length; i++) {

            String sinonimo = "";

            //ela tem precedencia sobre ele para substituicao
            for (String sufixo : sufixosElaArray) {
                for (Entry<String, String> entry : racasElaPrefix.entrySet()) {
                    sinonimo = entry.getKey() + sufixo;
                    if (sinonimo.equalsIgnoreCase(str[i])) {
                        str[i] = entry.getValue();
                    }
                }
            }

            for (String sufixo : sufixosEleArray) {
                for (Entry<String, String> entry : racasElePrefix.entrySet()) {
                    sinonimo = entry.getKey() + sufixo;
                    if (sinonimo.equalsIgnoreCase(str[i])) {
                        str[i] = entry.getValue();
                    }
                }
            }

        }

        return str;

    }

    public static ArrayList<String> toArrayList(String[] str) {
        ArrayList<String> retorno = new ArrayList<String>();
        retorno.addAll(Arrays.asList(str));
        return retorno;
    }

    public static String[] toArray(ArrayList<String> str) {
        String[] retorno = new String[str.size()];
        for (int i = 0; i < str.size(); i++) {
            retorno[i] = str.get(i);
        }
        return retorno;
    }

    public static String[] quebrarEFormatarString(String texto) {
        String[] textoFormatado = texto.split(" ");
        textoFormatado = removePreposicoes(textoFormatado);
        return textoFormatado;
    }
}
