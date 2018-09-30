package com.example.mfm.dicegameapp;

public class Player {
    private int finalScore;
    private boolean isComputer;
    private String name;

    public Player() {
        finalScore = 0;
        isComputer = false;
        name = "Player";
    }

    public void setComputer(boolean computer) {
        isComputer = computer;
    }

    public boolean isComputer() {
        return isComputer;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
