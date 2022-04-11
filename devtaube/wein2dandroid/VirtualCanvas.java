package devtaube.wein2dandroid;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class VirtualCanvas implements RenderCalls
{

    private final Bitmap bitmap;
    private final Canvas canvas;
    private final int width;
    private final int height;


    public VirtualCanvas(int width, int height)
    {
        this.bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        this.canvas = new Canvas(bitmap);
        this.width = width;
        this.height = height;
    }

    public Bitmap getBitmap() { return bitmap; };


    @Override
    public Canvas getGraphics() { return canvas; }

    @Override
    public int getWidth() { return width; }

    @Override
    public int getHeight() { return height; }

    @Override
    public boolean drawingAllowed() { return true; }

}
