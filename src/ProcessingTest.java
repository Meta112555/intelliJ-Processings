/**
 * Created by Johannes on 4/10/14.
 */

import com.etagefuenf.mb.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.video.Capture;

public class ProcessingTest extends PApplet{

    Capture cam;
    PImage img;

    public void setup(){
        cam = new Capture(this, 160, 160);
        cam.start();
        size(160, 160);
        background(0);
    }
    public void draw(){
        background(0);
        PImage snap = cam.get();

        ellipse(mouseX, mouseY, 30, 30);
    }
    public voic captureEvent(Capture cap) {
        cap.read();
    }
    static public void main(String args[]) {
        PApplet.main(new String[] { "ProcessingTest" });
    }
}
