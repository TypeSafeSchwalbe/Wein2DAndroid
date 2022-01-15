package devtaube.wein2dandroid;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.*;
import android.graphics.*;

public class App extends SurfaceView implements SurfaceHolder.Callback {
    // Variables ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected Context context;
    protected SoundPool soundPool;
    private Application app;
    private Gameloop gameloop;
    public int width;
    public int height;
    private boolean touching;
    private int touchX = -1;
    private int touchY = -1;
    // Methods ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Window Constructor
    // -> sets up basic API and creates Objects
    public App(Context givenContext, Application app) {
        // Android API
        super(givenContext);
        context = givenContext;
        getHolder().addCallback(this);
        setFocusable(true);
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        Sprite.app = this;
        Sound.app = this;
        // Paint for rendering
        drawPaint = new Paint();
        imageAlphaPaint = new Paint();
        textPaint = new Paint();
        drawRectRect = new Rect();
        drawBitmapSrcRect = new Rect();
        drawBitmapDestRect = new Rect();
        // store application
        this.app = app;
        // get Screen dimensions
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);
        width = displaySize.x;
        height = displaySize.y;
        // call onCreate on Application
        app.onCreate(this);
    }

    // Overriden Methods for SurfaceHolder.Callback
    // -> need to be added for Touch Input and other stuff
    // (Surface created)
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setWillNotDraw(false);
        setVisibility(View.VISIBLE);
        // Set up gameloop
        gameloop = new Gameloop(getHolder(), this);
        gameloop.start();
    }

    // (Touch input)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchX = (int) event.getX();
        touchY = (int) event.getY();
        int eventaction = event.getAction();
        switch (eventaction) {
            case MotionEvent.ACTION_DOWN:
                touching = true;
                break;
            case MotionEvent.ACTION_UP:
                touching = false;
                break;
        }
        return true;
    }

    // (Surface destroyed)
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (soundPool != null) {
            soundPool.release();
        }
        soundPool = null;
        gameloop.running = false;
        boolean tryStoppingThread = true;
        while (tryStoppingThread)
        {
            try
            {
                gameloop.join();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            tryStoppingThread = false;
        }
    }

    // Unused
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
    // Setters ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setFPS(int fps)
    {
        gameloop.fps = fps;
    }
    // Getters /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public int getFPS()
    {
        return gameloop.fps;
    }
    // Input ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public int getMouseX()
    {
        return touchX;
    }
    public int getMouseY()
    {
        return touchY;
    }
    public boolean getMouseL()
    {
        return touching;
    }
    // onFrame ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Canvas canvas;
    final Paint drawPaint;
    final Paint imageAlphaPaint;
    final Paint textPaint;
    final Rect drawRectRect;
    final Rect drawBitmapDestRect;
    final Rect drawBitmapSrcRect;
    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        this.canvas = canvas;
        app.onFrame();
    }
    // Rendering Methods ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void drawRect(int posX, int posY, int sizeX, int sizeY, int colorR, int colorG, int colorB)
    {
        drawRectRect.set(posX, posY, posX + sizeX, posY + sizeY);
        drawPaint.setARGB(255, colorR, colorG, colorB);
        canvas.drawRect(drawRectRect, drawPaint);
    }
    public void drawRect(int posX, int posY, int sizeX, int sizeY, int colorA, int colorR, int colorG, int colorB)
    {
        drawRectRect.set(posX, posY, posX + sizeX, posY + sizeY);
        drawPaint.setARGB(colorA, colorR, colorG, colorB);
        canvas.drawRect(drawRectRect, drawPaint);
    }

    public void drawOval(int posX, int posY, int sizeX, int sizeY, int colorR, int colorG, int colorB)
    {
        drawPaint.setARGB(255, colorR, colorG, colorB);
        canvas.drawOval(posX, posY, posX + sizeX, posY + sizeY, drawPaint);
    }
    public void drawOval(int posX, int posY, int sizeX, int sizeY, int colorA, int colorR, int colorG, int colorB)
    {
        drawPaint.setARGB(colorA, colorR, colorG, colorB);
        canvas.drawOval(posX, posY, posX + sizeX, posY + sizeY, drawPaint);
    }

    public void drawSprite(Sprite sprite, int posX, int posY)
    {
        canvas.drawBitmap(sprite.getBitmap(), posX, posY, null);
    }
    public void drawSprite(Sprite sprite, int posX, int posY, int colorA)
    {
        imageAlphaPaint.setAlpha(colorA);
        canvas.drawBitmap(sprite.getBitmap(), posX, posY, imageAlphaPaint);
    }
    public void drawSprite(Sprite sprite, int posX, int posY, int sizeX, int sizeY)
    {
        drawBitmapDestRect.set(posX, posY, posX + sizeX, posY + sizeY);
        canvas.drawBitmap(sprite.getBitmap(), null, drawBitmapDestRect, null);
    }
    public void drawSprite(Sprite sprite, int posX, int posY, int sizeX, int sizeY, int colorA)
    {
        imageAlphaPaint.setAlpha(colorA);
        drawBitmapDestRect.set(posX, posY, posX + sizeX, posY + sizeY);
        canvas.drawBitmap(sprite.getBitmap(), null, drawBitmapDestRect, imageAlphaPaint);
    }
    public void drawSprite(Sprite sprite, int posX, int posY, int sizeX, int sizeY, int srcPosX, int srcPosY, int srcSizeX, int srcSizeY)
    {
        drawBitmapSrcRect.set(srcPosX, srcPosY, srcPosX + srcSizeX, srcPosY + srcSizeY);
        drawBitmapDestRect.set(posX, posY, posX + sizeX, posY + sizeY);
        canvas.drawBitmap(sprite.getBitmap(), drawBitmapSrcRect, drawBitmapDestRect, null);
    }
    public void drawSprite(Sprite sprite, int posX, int posY, int sizeX, int sizeY, int srcPosX, int srcPosY, int srcSizeX, int srcSizeY, int colorA)
    {
        imageAlphaPaint.setAlpha(colorA);
        drawBitmapSrcRect.set(srcPosX, srcPosY, srcPosX + srcSizeX, srcPosY + srcSizeY);
        drawBitmapDestRect.set(posX, posY, posX + sizeX, posY + sizeY);
        canvas.drawBitmap(sprite.getBitmap(), drawBitmapSrcRect, drawBitmapDestRect, imageAlphaPaint);
    }

    public void drawText(String content, int posX, int posY, int fontSize, String fontFamily, int colorR, int colorG, int colorB)
    {
        textPaint.setARGB(255, colorR, colorG, colorB);
        textPaint.setTextSize(fontSize);
        textPaint.setTypeface(Typeface.create(fontFamily, Typeface.NORMAL));
        textPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(content, posX, posY + fontSize, textPaint);
    }
    public void drawText(String content, int posX, int posY, int fontSize, String fontFamily, int colorA, int colorR, int colorG, int colorB)
    {
        textPaint.setARGB(colorA, colorR, colorG, colorB);
        textPaint.setTextSize(fontSize);
        textPaint.setTypeface(Typeface.create(fontFamily, Typeface.NORMAL));
        textPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(content, posX, posY + fontSize, textPaint);
    }
    public void drawText(String content, int posX, int posY, String positioning, int fontSize, String fontFamily, int colorR, int colorG, int colorB)
    {
        textPaint.setARGB(255, colorR, colorG, colorB);
        textPaint.setTextSize(fontSize);
        textPaint.setTypeface(Typeface.create(fontFamily, Typeface.NORMAL));
        textPaint.setTextAlign(Paint.Align.LEFT);
        switch(positioning)
        {
            case "LEFT":
                textPaint.setTextAlign(Paint.Align.RIGHT);
                break;
            case "CENTER":
                textPaint.setTextAlign(Paint.Align.CENTER);
                break;
        }
        canvas.drawText(content, posX, posY + fontSize, textPaint);
    }
    public void drawText(String content, int posX, int posY, String positioning, int fontSize, String fontFamily, int colorA, int colorR, int colorG, int colorB)
    {
        textPaint.setARGB(colorA, colorR, colorG, colorB);
        textPaint.setTextSize(fontSize);
        textPaint.setTypeface(Typeface.create(fontFamily, Typeface.NORMAL));
        textPaint.setTextAlign(Paint.Align.LEFT);
        switch(positioning)
        {
            case "LEFT":
                textPaint.setTextAlign(Paint.Align.RIGHT);
                break;
            case "CENTER":
                textPaint.setTextAlign(Paint.Align.CENTER);
                break;
        }
        canvas.drawText(content, posX, posY + fontSize, textPaint);
    }

    public void fill(int colorR, int colorG, int colorB)
    {
        canvas.drawARGB(255, colorR, colorG, colorB);
    }
    public void fill(int colorA, int colorR, int colorG, int colorB)
    {
        canvas.drawARGB(colorA, colorR, colorG, colorB);
    }

    public void drawLine(int posX, int posY, int endX, int endY, int lineWidth, int colorR, int colorG, int colorB)
    {
        drawPaint.setARGB(255, colorR, colorG, colorB);
        drawPaint.setStrokeWidth(lineWidth);
        canvas.drawLine(posX, posY, endX, endY, drawPaint);
    }
    public void drawLine(int posX, int posY, int endX, int endY, int lineWidth, int colorA, int colorR, int colorG, int colorB)
    {
        drawPaint.setARGB(colorA, colorR, colorG, colorB);
        drawPaint.setStrokeWidth(lineWidth);
        canvas.drawLine(posX, posY, endX, endY, drawPaint);
    }
}
