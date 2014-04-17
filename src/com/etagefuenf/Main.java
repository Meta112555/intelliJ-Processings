package com.etagefuenf;

import com.etagefuenf.Filter.ProcessingTest;
import processing.core.PApplet;
import processing.video.Capture;

import java.util.Arrays;
import java.util.List;

public class Main extends ProcessingTest {
    private List<ProcessingTest> children;

    private int size = 3;
    private int zoom = 1;
    private int w = 160 * size;
    private int h = 120 * size;

    public void setup(){
        children = Arrays.asList(new ProcessingTest());
        children.forEach(n->n.setup());
        size(w * zoom * 2 + 1, h * zoom * 2 + 1);
    }
    public void draw(){
        children.forEach(n->{
            n.draw();
            image(n.preRenderer, 0,0);
        });

    }
    public void captureEvent(Capture c){}
    public void keyPressed(){}
    public static void main(String[] args) {
        PApplet.main(new String[] {"com.etagefuenf.Main"});
    }
}
