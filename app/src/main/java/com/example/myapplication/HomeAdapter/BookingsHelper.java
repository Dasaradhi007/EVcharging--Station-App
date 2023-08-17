package com.example.myapplication.HomeAdapter;

public class BookingsHelper {
    int image;
    String tile,status,connector,description;

    public BookingsHelper(int image, String tile, String status, String connector, String description) {
        this.image = image;
        this.tile = tile;
        this.status = status;
        this.connector = connector;
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public String getTile() {
        return tile;
    }

    public String getStatus() {
        return status;
    }

    public String getConnector() {
        return connector;
    }

    public String getDescription() {
        return description;
    }
}
