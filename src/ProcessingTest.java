

/**
 * Created by Johannes on 4/10/14.
 */
//http://steve-yegge.blogspot.de/2006/03/execution-in-kingdom-of-nouns.html

import martinSchneider.Filter.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.video.Capture;

public class ProcessingTest extends PApplet{

    private Capture cam;

    private int size = 3;
    private int zoom = 1;

    private int w = 160 * size;
    private int h = 120 * size;

    private PImage snap;
    private Filter RedFilter;
    private Filter GreenFilter;
    private Filter BlueFilter;

    public void setup(){

        size(w * zoom * 2 + 1, h * zoom * 2 + 1);

        cam = new Capture(this, w, h);
        cam.start();

        RedFilter = new Filter((int x, int y, int w, int h, int[] pixels) -> {
            int[] colors = Filter.analyseColor(pixels[y * w + x]);
            return (0x00 << 24) | (colors[1] << 16) | (0x00 << 8) | 0x00;
        });
        GreenFilter = new Filter((int x, int y, int w, int h, int[] pixels) -> {
            int[] colors = Filter.analyseColor(pixels[y * w + x]);
            return (0x00 << 24) | (0x00 << 16) | (colors[2] << 8) | 0x00;
        });
        BlueFilter = new Filter((int x, int y, int w, int h, int[] pixels) -> {
            int[] colors = Filter.analyseColor(pixels[y * w + x]);
            return (0x00 << 24) | (0x00 << 16) | (0x00 << 8) | colors[3];
        });

        setupDisplay();
    }

    public void draw(){

        snap = cam.get();
        // top left
        show(snap, "original", 0, 0);
        // top right
        show(RedFilter.edit(snap), "red", 1, 0);
        // bottom left
        show(GreenFilter.edit(snap), "green", 0, 1);
        // bottom right
        show(BlueFilter.edit(snap), "blue", 1, 1);
    }

    public void captureEvent(Capture cap) {
        cap.read();
    }
    static public void main(String args[]) {
        PApplet.main(new String[] { "ProcessingTest"});
        //PApplet.main(new String[] { "--present","ProcessingTest" });
    }

    // make sure to rescale font size and strokeweight
    void setupDisplay() {
        textFont(createFont("", 10.0f / zoom));
        strokeWeight(1.0f / zoom);
    }
    void show(PImage img, String label, int px, int py) {
        float ymargin = 4.0f / zoom;
        float xmargin = 4.0f / zoom;
        // show image
        image(img, px * w, py * h);
        // draw label
        label(label, px * w + xmargin, py * h + ymargin);
        // image outline
        noFill(); stroke(0); rect(px * w, py * h, w, h);
    }
    // nice transparent label (black text on a white box)
    void label(String txt, float x, float y) {

        int alpha = 200;
        float xpad = 16.0f / zoom;
        float ypad = 4.0f / zoom;
        float ta = textAscent();
        float th = textAscent() + textDescent();
        float tw = textWidth(txt);

        pushStyle();
        noStroke();
        fill(255, alpha); rect(x, y, tw + 2 * xpad, th + 2 * ypad);
        fill(0, alpha); text(txt, x + xpad, y + ypad + ta);
        popStyle();
    }
}