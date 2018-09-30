package com.example.mfm.dicegameapp;

import android.graphics.drawable.Drawable;

public class DiceSides {

    public enum DiceSideName{
        SIDE_1,
        SIDE_2,
        SIDE_3,
        SIDE_4,
        SIDE_5,
        SIDE_6
    }

    public DiceSideName sideName;
    public int sideValue;
    public Drawable sideImage;

    public DiceSides() { }

    public void setSideImage(Drawable sideImage) {
        this.sideImage = sideImage;
    }

    public Drawable getSideImage() {
        return sideImage;
    }

    public void setSideValue(int sideValue) {
        this.sideValue = sideValue;
    }

    public int getSideValue() {
        return sideValue;
    }

    public DiceSideName getSideName() {
        return sideName;
    }

    public void setSideName(DiceSideName sideName) {
        this.sideName = sideName;
    }
}
