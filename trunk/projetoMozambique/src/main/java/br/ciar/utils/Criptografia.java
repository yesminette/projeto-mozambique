/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ciar.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lauro
 */
public class Criptografia {

    public static String criptografarMD5(String texto) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            byte[] data = texto.getBytes();
            m.update(data, 0, data.length);
            BigInteger i = new BigInteger(1, m.digest());
            return String.format("%1$032X", i);
        } catch (NoSuchAlgorithmException ex) {
            return texto;
        }
    }
}
