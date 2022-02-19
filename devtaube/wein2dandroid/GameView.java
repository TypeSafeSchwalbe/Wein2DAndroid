package devtaube.wein2dandroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{

    protected Wein2DApplication wein2DApplication;
    protected SoundPool soundPool;
    protected Gameloop gameloop;

    protected boolean touching;
    protected int touchX = -1;
    protected int touchY = -1;

    protected int fps = 60;

    private Canvas canvas;
    final Paint drawPaint;
    final Paint imageAlphaPaint;
    final Paint textPaint;
    final Rect drawRectRect;
    final Rect drawBitmapDestRect;
    final Rect drawBitmapSrcRect;

    public GameView(Wein2DApplication wein2DApplication)
    {
        super(wein2DApplication);
        this.wein2DApplication = wein2DApplication;
        this.getHolder().addCallback(this);
        this.setFocusable(true);
        Sprite.gameView = this;
        Sound.gameView = this;
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        drawPaint = new Paint();
        imageAlphaPaint = new Paint();
        textPaint = new Paint();
        drawRectRect = new Rect();
        drawBitmapSrcRect = new Rect();
        drawBitmapDestRect = new Rect();
        WindowManager wm = (WindowManager) wein2DApplication.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);
        wein2DApplication.width = displaySize.x;
        wein2DApplication.height = displaySize.y;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setWillNotDraw(false);
        setVisibility(View.VISIBLE);
        gameloop = new Gameloop(this);
        gameloop.fps = fps;
        gameloop.start();
    }

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

    private long lastFrameTime;

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        gameloop.fps = fps;
        this.canvas = canvas;
        long time = System.nanoTime();
        wein2DApplication.deltaTime = (double) (time - lastFrameTime) / 1_000_000_000;
        lastFrameTime = time;
        wein2DApplication.onFrame();
    }

    protected void drawRect(double posX, double posY, double sizeX, double sizeY, int colorA, int colorR, int colorG, int colorB)
    {
        drawRectRect.set((int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posX + sizeX), (int) Math.floor(posY + sizeY));
        drawPaint.setARGB(colorA, colorR, colorG, colorB);
        canvas.drawRect(drawRectRect, drawPaint);
    }

    protected void drawOval(double posX, double posY, double sizeX, double sizeY, int colorA, int colorR, int colorG, int colorB)
    {
        drawPaint.setARGB(colorA, colorR, colorG, colorB);
        canvas.drawOval((int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posX + sizeX), (int) Math.floor(posY + sizeY), drawPaint);
    }

    protected void drawSprite(Sprite sprite, double posX, double posY, double sizeX, double sizeY, int srcPosX, int srcPosY, int srcSizeX, int srcSizeY, int colorA)
    {
        imageAlphaPaint.setAlpha(colorA);
        drawBitmapSrcRect.set(srcPosX, srcPosY, srcPosX + srcSizeX, srcPosY + srcSizeY);
        drawBitmapDestRect.set((int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posX + sizeX), (int) Math.floor(posY + sizeY));
        canvas.drawBitmap(sprite.getBitmap(), drawBitmapSrcRect, drawBitmapDestRect, imageAlphaPaint);
    }

    protected void drawText(String content, double posX, double posY, int positioning, double fontSize, String fontFamily, int colorA, int colorR, int colorG, int colorB)
    {
        textPaint.setARGB(colorA, colorR, colorG, colorB);
        textPaint.setTextSize((int) Math.floor(fontSize));
        textPaint.setTypeface(Typeface.create(fontFamily, Typeface.NORMAL));
        textPaint.setTextAlign(Paint.Align.LEFT);
        switch(positioning)
        {
            case TextPositioning.LEFT:
                textPaint.setTextAlign(Paint.Align.RIGHT);
                break;
            case TextPositioning.CENTER:
                textPaint.setTextAlign(Paint.Align.CENTER);
                break;
        }
        canvas.drawText(content, (int) Math.floor(posX), (int) Math.floor(posY + fontSize), textPaint);
    }

    protected void fill(int colorA, int colorR, int colorG, int colorB)
    {
        canvas.drawARGB(colorA, colorR, colorG, colorB);
    }

    protected void drawLine(double posX, double posY, double endX, double endY, double lineWidth, int colorA, int colorR, int colorG, int colorB)
    {
        drawPaint.setARGB(colorA, colorR, colorG, colorB);
        drawPaint.setStrokeWidth((int) Math.floor(lineWidth));
        canvas.drawLine((int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(endX), (int) Math.floor(endY), drawPaint);
    }

}
