package devtaube.wein2dandroid;

import android.graphics.Paint;
import android.graphics.Rect;

class RenderCallsUtils
{

    static final Paint DRAWPAINT;
    static final Paint IMAGEALPHAPAINT;
    static final Paint TEXTPAINT;

    static final Rect DRAWRECTRECT;
    static final Rect DRAWBITMAPSRCRECT;
    static final Rect DRAWBITMAPDESTRECT;

    static
    {
        DRAWPAINT = new Paint();
        IMAGEALPHAPAINT = new Paint();
        IMAGEALPHAPAINT.setAntiAlias(false);
        IMAGEALPHAPAINT.setFilterBitmap(false);
        TEXTPAINT = new Paint();

        DRAWRECTRECT = new Rect();
        DRAWBITMAPSRCRECT = new Rect();
        DRAWBITMAPDESTRECT = new Rect();
    }

}
