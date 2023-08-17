package com.example.myapplication.HomeAdapter;

public class nearHelperClass {
    int image;
    String tile,description,connector;

    public nearHelperClass(int image, String tile, String description, String connector) {
        this.image = image;
        this.tile = tile;
        this.description = description;
        this.connector = connector;
    }

    public int getImage() {
        return image;
    }

    public String getTile() {
        return tile;
    }

    public String getDescription() {
        return description;
    }

    public String getConnector() {
        return connector;
    }
}
