package com.apis.pruebas;

import com.google.gson.Gson;
import com.squareup.okhttp.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class CatService {


    public static void seeCats() throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/images/search")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        String elJson = response.body().string();

        elJson = elJson.substring(1, elJson.length());
        elJson = elJson.substring(0, elJson.length() - 1);
        Gson gson = new Gson();
        Cat cats = gson.fromJson(elJson, Cat.class);
        Image image = null;
        try {
            URL url = new URL(cats.getUrl());
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.addRequestProperty("User-Agent", "");
            BufferedImage bufferedImage = ImageIO.read(http.getInputStream());
            ImageIcon backGroundCats = new ImageIcon(bufferedImage);
            if (backGroundCats.getIconWidth() > 800) {
                Image backGround = backGroundCats.getImage();
                Image modificada = backGround.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
                backGroundCats = new ImageIcon(modificada);
            }
            String menu = " Opciones : \n" +
                    "1. Ver otra imagen \n" +
                    "2. Favorito \n" +
                    "3. Volver ";
            String[] botones = {"ver otra imagen ", "Favorito", "Volver"};
            String id_cat = cats.getId();
            String opcion = (String) JOptionPane.showInputDialog(null, menu, id_cat, JOptionPane.INFORMATION_MESSAGE, backGroundCats, botones, botones[0]);
            int seleccion = -1;
            for (int i = 0; i < botones.length; i++) {
                if (opcion.equals(botones[i]))
                    seleccion = i;
            }
            switch (seleccion) {
                case 0:
                    seeCats();
                    break;
                case 1:
                    favoriteCat(cats);
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void favoriteCat(Cat cat_s) {
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n\t\"image_id\":\"" + cat_s.getId() + "\"\n}");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", cat_s.getApiKey())
                    .build();
            Response response = client.newCall(request).execute();
            System.out.println(cat_s.getApiKey());

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public static void seeFavorites(String cats) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .method("GET", null)
                    .addHeader("x-api-key", "" + cats + "")
                    .build();
            Response response = client.newCall(request).execute();
            String elJson = response.body().string();
            Gson gson = new Gson();
            CatFavoris[] catArray = gson.fromJson(elJson, CatFavoris[].class);

            if (catArray.length > 0) {
                int minimo = 1;
                int maximo = catArray.length;
                int aleatorio = (int) (Math.random() * ((maximo - minimo) + 1)) + minimo;
                int indice = aleatorio - 1;

                CatFavoris catfavoris = catArray[indice];

                Image image = null;
                try {
                    URL url = new URL(catfavoris.image.getUrl());
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    http.addRequestProperty("User-Agent", "");
                    BufferedImage bufferedImage = ImageIO.read(http.getInputStream());
                    ImageIcon backGroundCats = new ImageIcon(bufferedImage);
                    if (backGroundCats.getIconWidth() > 800) {
                        Image backGround = backGroundCats.getImage();
                        Image modificada = backGround.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
                        backGroundCats = new ImageIcon(modificada);
                    }
                    String menu = " Opciones : \n" +
                            "1. Ver otra imagen \n" +
                            "2. Eliminar Favorito \n" +
                            "3. Volver ";
                    String[] botones = {"ver otra imagen ", "Eliminar Favorito", "Volver"};
                    String id_cat = catfavoris.getId();
                    String opcion = (String) JOptionPane.showInputDialog(null, menu, id_cat, JOptionPane.INFORMATION_MESSAGE, backGroundCats, botones, botones[0]);
                    int seleccion = -1;
                    for (int i = 0; i < botones.length; i++) {
                        if (opcion.equals(botones[i]))
                            seleccion = i;
                    }
                    switch (seleccion) {
                        case 0:
                            seeFavorites(catfavoris.apikey);
                            break;
                        case 1:
                            deleteFavorie(catfavoris);
                            break;
                        case 3:
                            break;
                        default:
                            break;
                    }
                } catch (IOException e) {
                    System.out.println(e);
                }

            }


        } catch (IOException exc) {
            System.out.println(exc);
        }

    }

    private static void deleteFavorie(CatFavoris catFavoris) {
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites/" + catFavoris.getId() + "")
                    .method("DELETE", body)
                    .addHeader("x-api-key", catFavoris.getApikey())
                    .build();
            Response response = client.newCall(request).execute();
        } catch (IOException excep) {
            System.out.println(excep);

        }

    }
}
