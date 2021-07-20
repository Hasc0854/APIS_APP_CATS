package com.apis.pruebas;

public class CatFavoris {
    String id;
    String user_id;
    String imagen_id;
    String apikey="28f1d1ac-74a1-4900-a37e-9ea6da3d5997";
    ImagenX image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getImagen_id() {
        return imagen_id;
    }

    public void setImagen_id(String imagen_id) {
        this.imagen_id = imagen_id;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public ImagenX getImage() {
        return image;
    }

    public void setImage(ImagenX image) {
        this.image = image;
    }
}
