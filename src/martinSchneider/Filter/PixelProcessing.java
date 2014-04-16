package martinSchneider.Filter;

/**
 * Created by Johannes on 4/15/14.
 */

public interface PixelProcessing {
    public int doToPixel(int x, int y, int w, int h, int[] source);
}
