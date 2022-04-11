package devtaube.wein2dandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public final class Sprite
{

    private Bitmap bitmap;


    public Sprite(int resource)
    {
        bitmap = BitmapFactory.decodeResource(Wein2DApplication.WEIN2DAPPLICATION.getResources(), resource);
    }

    public Bitmap getBitmap()
    {
        return bitmap;
    }

    public int getWidth()
    {
        return bitmap.getWidth();
    }
    public int getHeight()
    {
        return bitmap.getHeight();
    }

}
