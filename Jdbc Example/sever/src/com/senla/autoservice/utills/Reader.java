package com.senla.autoservice.utills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Reader {

    public static String readline() throws IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        final String response = br.readLine();
        return response;
    }

    public static Integer readInt() throws IOException, NumberFormatException{
        return Integer.parseInt(readline());
    }

    public static Float readFloat() throws IOException, NumberFormatException{
        return Float.parseFloat(readline());
    }

}