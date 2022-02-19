package devtaube.wein2dandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Sprite
{
    private Bitmap bitmap;
    protected static GameView gameView;

    public Sprite(int resource)
    {
        bitmap = BitmapFactory.decodeResource(gameView.getResources(), resource);
    }

    protected Bitmap getBitmap()
    {
        return bitmap;
    }

    public final int getWidth()
    {
        return bitmap.getWidth();
    }
    public final int getHeight()
    {
        return bitmap.getHeight();
    }



}
