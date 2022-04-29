package com.jesusguzman.pokedexapp;

import java.util.ArrayList;

public class Pokemon {

    private int id;
    private String name;
    private String url;
    private int height;
    private int weight;
    private String type;
    private ArrayList<PokemonMove> moveList;

    public int getId() {
        String[] urlID = url.split("/");
        return Integer.parseInt(urlID[urlID.length-1]);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<PokemonMove> getMoveList() {
        return moveList;
    }

    public void setMoveList(ArrayList<PokemonMove> moveList) {
        this.moveList = moveList;
    }
}
