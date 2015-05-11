/*
 * Nombre: Cuenta.java
 * Version 0.1
 * Autor: Ismael Rodriguez.
 * Fecha: 27-3-15
 * Descripcion: Este fichero implementa funciones utiles diversas 
 * para el desarrollo de la aplicacion.
 * Copyright (C) 2015 Hyena Technologies
 */
package persistencia;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utiles {

    /**
     * Pre: input!=null
     * Post: Genera un Hash para el texto pasado.
     * Fuente: http://viralpatel.net/blogs/java-md5-hashing-salting-password/
     */
    public static String generateHash(String input) {

        String md5 = null;

        if (null == input) return null;

        try {


            MessageDigest digest = MessageDigest.getInstance("MD5");


            digest.update(input.getBytes(), 0, input.length());

            md5 = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
        return md5;
    }
}
