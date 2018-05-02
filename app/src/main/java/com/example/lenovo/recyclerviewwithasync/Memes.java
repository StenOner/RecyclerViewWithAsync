package com.example.lenovo.recyclerviewwithasync;

import android.graphics.Bitmap;

public class Memes {
    String name;
    Bitmap photoId;
    String desc;

    public Memes(String name, String desc, Bitmap photoId) {
        this.name=name;
        this.desc=desc;
        this.photoId=photoId;
    }
}
