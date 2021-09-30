package wein2dandroid;

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
    private int screenWidth;
    private int screenHeight;
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
        screenWidth = displaySize.x;
        screenHeight = displaySize.y;
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
    // Getters ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public int getSizeX()
    {
        return screenWidth;
    }
    public int getSizeY()
    {
        return screenHeight;
    }
    // Input ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public int getTouchX()
    {
        return touchX;
    }
    public int getTouchY()
    {
        return touchY;
    }
    public boolean getTouching()
    {
        return touching;
    }
    // onFrame ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Canvas canvas;
    final Paint drawPaint;
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
    public void fill(int colorR, int colorG, int colorB)
    {
        canvas.drawARGB(255, colorR, colorG, colorB);
    }
    public void drawRect(int posX, int posY, int sizeX, int sizeY, int colorR, int colorG, int colorB)
    {
        drawRectRect.set(posX, posY, posX + sizeX, posY + sizeY);
        drawPaint.setARGB(255, colorR, colorG, colorB);
        canvas.drawRect(drawRectRect, drawPaint);
    }
    public void drawOval(int posX, int posY, int sizeX, int sizeY, int colorR, int colorG, int colorB)
    {
        drawPaint.setARGB(255, colorR, colorG, colorB);
        canvas.drawOval(posX, posY, posX + sizeX, posY + sizeY, drawPaint);
    }
    public void drawSprite(Sprite givenSprite, int posX, int posY) {
        canvas.drawBitmap(givenSprite.getBitmap(), posX, posY, null);
    }
    public void drawSprite(Sprite givenSprite, int posX, int posY, int sizeX, int sizeY) {
        drawBitmapDestRect.set(posX, posY, posX + sizeX, posY + sizeY);
        canvas.drawBitmap(givenSprite.getBitmap(), null, drawBitmapDestRect, null);
    }
    public void drawSprite(Sprite givenSprite, int posX, int posY, int sizeX, int sizeY, int sourcePosX, int sourcePosY, int sourceSizeX, int sourceSizeY) {
        drawBitmapSrcRect.set(sourcePosX, sourcePosY, sourcePosX + sourceSizeX, sourcePosY + sourceSizeY);
        drawBitmapDestRect.set(posX, posY, posX + sizeX, posY + sizeY);
        canvas.drawBitmap(givenSprite.getBitmap(), drawBitmapSrcRect, drawBitmapDestRect, null);
    }
}