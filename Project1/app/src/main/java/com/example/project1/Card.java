package com.example.project1;

public class Card {
    private int imgId;
    private int arrayId;

    public Card(int arrayId, int imgId) {
        this.imgId = imgId;
        this.arrayId = arrayId;
    }

    public int getArrayId() {
        return arrayId;
    }

    public int getImgId() {
        return imgId;
    }

    public void setArrayId(int arrayId) {
        this.arrayId = arrayId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
