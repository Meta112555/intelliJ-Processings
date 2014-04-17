package com.etagefuenf.Filter;

/**
 * Created by Johannes on 4/10/14.
 */
//http://steve-yegge.blogspot.de/2006/03/execution-in-kingdom-of-nouns.html

import com.etagefuenf.Functions.BitShiftFunctions;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.video.Capture;

import static com.etagefuenf.Functions.BitShiftFunctions.fastBlur;

public class ProcessingTest extends PApplet{

    private Capture cam;

    private int size = 3;
    private int zoom = 1;

    private int w = 160 * size;
    private int h = 120 * size;

    public PGraphics preRenderer;

    private PImage snap;
    private Filter RedFilter;
    private Filter GreenFilter;
    private Filter BlueFilter;

    public void setup(){
        size(w * zoom * 2 + 1, h * zoom * 2 + 1);
        preRenderer = createGraphics(this.width, this.height);

        cam = new Capture(this, w, h);
        cam.start();

        RedFilter = new Filter((int x, int y, int w, int h, int[] pixels) -> {
            int[] colors = BitShiftFunctions.analyseColor(pixels[y * w + x]);
            return (0x10 << 24) | (colors[1] << 16) | (0x00 << 8) | 0x00;
        });
        GreenFilter = new Filter((int x, int y, int w, int h, int[] pixels) -> {
            int[] colors = BitShiftFunctions.analyseColor(pixels[y * w + x]);
            return (0x10 << 24) | (0x00 << 16) | (colors[2] << 8) | 0x00;
        });
        BlueFilter = new Filter((int x, int y, int w, int h, int[] pixels) -> {
            int[] colors = BitShiftFunctions.analyseColor(pixels[y * w + x]);
            return (0x10 << 24) | (0x00 << 16) | (0x00 << 8) | colors[3];
        });

        setupDisplay();
    }

    public void draw(){
        snap = cam.get();
        preRenderer.beginDraw();
        preRenderer.background(0,0);
        show(snap, "original", 0, 0);
        show(RedFilter.edit(snap), "red", 1, 0);
        show(GreenFilter.edit(snap), "green", 0, 1);
        show(BlueFilter.edit(snap), "blue", 1, 1);
        preRenderer.endDraw();
        preRenderer.loadPixels();
        fastBlur(preRenderer,mouseX/10);
        image(preRenderer,0,0);
    }

    public void captureEvent(Capture cap) {
        cap.read();
    }
    static public void main(String args[]) {
        PApplet.main(new String[] { "com.etagefuenf.Filter.ProcessingTest"});
        //PApplet.main(new String[] { "--present","com.etagefuenf.Filter.ProcessingTest" });
    }

    // make sure to rescale font size and strokeweight
    void setupDisplay() {
        preRenderer.textFont(createFont("", 10.0f / zoom));
        preRenderer.strokeWeight(1.0f / zoom);
    }
    void show(PImage img, String label, int px, int py) {
        float ymargin = 4.0f / zoom;
        float xmargin = 4.0f / zoom;
        // show image
        preRenderer.image(img, px * w, py * h);
        // draw label
        label(label, px * w + xmargin, py * h + ymargin);
        // image outline
        preRenderer.noFill();
        preRenderer.stroke(0);
        preRenderer.rect(px * w, py * h, w, h);
    }
    // nice transparent label (black text on a white box)
    void label(String txt, float x, float y) {

        int alpha = 200;
        float xpad = 16.0f / zoom;
        float ypad = 4.0f / zoom;
        float ta = textAscent();
        float th = textAscent() + textDescent();
        float tw = textWidth(txt);

        preRenderer.pushStyle();
        preRenderer.noStroke();
        preRenderer.fill(255, alpha); preRenderer.rect(x, y, tw + 2 * xpad, th + 2 * ypad);
        preRenderer.fill(0, alpha); preRenderer.text(txt, x + xpad, y + ypad + ta);
        preRenderer.popStyle();
    }
}