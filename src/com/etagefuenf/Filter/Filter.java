package com.etagefuenf.Filter;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johannes on 4/15/14.
 */

public class Filter extends PApplet {
    public static List<Filter> Filters = new ArrayList<Filter>();

    private PixelProcessing Func;

    public Filter(PixelProcessing Func) {
        this.Func = Func;
        Filters.add(this);
    }

    public PImage edit(PImage img){
        return edit(img, this.Func);
    }

    public PImage edit(PImage img, PixelProcessing Func){
        int w = img.width, h = img.height;
        PImage result = createImage(w,h, RGB);
        img.loadPixels();
        for(int y = 0; y < h; y++) {
            for(int x = 0; x < w; x++) {
                result.pixels[y * w + x] = Func.doToPixel(x, y, w, h, img.pixels);
            }
        }
        result.updatePixels();
        return result;
    }

}