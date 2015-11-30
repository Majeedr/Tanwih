package com.majeedr.tanwih;

import android.view.View;

/**
 * Class manages tanwih data, view collection, etc.
 */
public class Tanwih {
    public int id;
    public String title;
    public String content;
    public String author;
    public String[] links;

    public Tanwih(int id, String author, String title, String content) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
    }
}
