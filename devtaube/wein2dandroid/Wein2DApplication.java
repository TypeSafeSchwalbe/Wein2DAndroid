package devtaube.wein2dandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public abstract class Wein2DApplication extends Activity
{

    protected GameView gameView;

    // ABSTRACT METHODS

    public abstract void onCreate();
    public abstract void onFrame();

    // ANDROID API

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        gameView = new GameView(this);
        FileIOMethods.wein2DApplication = this;
        this.setContentView(gameView);
        onCreate();
    }

    // VARIABLES

    public int width;
    public int height;
    public double deltaTime;

    // METHODS

    // getters

    public final int getFPS()
    {
        return gameView.gameloop.getFPS();
    }

    // setters

    public final void setTargetedFPS(int fps) { System.out.println(gameView); gameView.gameloop.fps = fps; }

    // input

    public final int getMouseX()
    {
        return gameView.touchX;
    }

    public final int getMouseY()
    {
        return gameView.touchY;
    }

    public final boolean getMouseL()
    {
        return gameView.touching;
    }

    // drawing

    public final void drawRect(double posX, double posY, double sizeX, double sizeY, int colorR, int colorG, int colorB)
    {
        gameView.drawRect(posX, posY, sizeX, sizeY, 255, colorR, colorG, colorB);
    }

    public final void drawRect(double posX, double posY, double sizeX, double sizeY, int colorA, int colorR, int colorG, int colorB)
    {
        gameView.drawRect(posX, posY, sizeX, sizeY, colorA, colorR, colorG, colorB);
    }

    public final void drawOval(double posX, double posY, double sizeX, double sizeY, int colorR, int colorG, int colorB)
    {
        gameView.drawOval(posX, posY, sizeX, sizeY, 255, colorR, colorG, colorB);
    }

    public final void drawOval(double posX, double posY, double sizeX, double sizeY, int colorA, int colorR, int colorG, int colorB)
    {
        gameView.drawOval(posX, posY, sizeX, sizeY, colorA, colorR, colorG, colorB);
    }

    public final void drawSprite(Sprite sprite, double posX, double posY)
    {
        gameView.drawSprite(sprite, posX, posY, sprite.getWidth(), sprite.getHeight(), 0, 0, sprite.getWidth(), sprite.getHeight(), 255);
    }

    public final void drawSprite(Sprite sprite, double posX, double posY, int colorA)
    {
        gameView.drawSprite(sprite, posX, posY, sprite.getWidth(), sprite.getHeight(), 0, 0, sprite.getWidth(), sprite.getHeight(), colorA);
    }

    public final void drawSprite(Sprite sprite, double posX, double posY, double sizeX, double sizeY)
    {
        gameView.drawSprite(sprite, posX, posY, sizeX, sizeY, 0, 0, sprite.getWidth(), sprite.getHeight(), 255);
    }

    public final void drawSprite(Sprite sprite, double posX, double posY, double sizeX, double sizeY, int colorA)
    {
        gameView.drawSprite(sprite, posX, posY, sizeX, sizeY, 0, 0, sprite.getWidth(), sprite.getHeight(), colorA);
    }

    public final void drawSprite(Sprite sprite, double posX, double posY, double sizeX, double sizeY, int srcPosX, int srcPosY, int srcSizeX, int srcSizeY)
    {
        gameView.drawSprite(sprite, posX, posY, sizeX, sizeY, srcPosX, srcPosY, srcSizeX, srcSizeY, 255);
    }

    public final void drawSprite(Sprite sprite, double posX, double posY, double sizeX, double sizeY, int srcPosX, int srcPosY, int srcSizeX, int srcSizeY, int colorA)
    {
        gameView.drawSprite(sprite, posX, posY, sizeX, sizeY, srcPosX, srcPosY, srcSizeX, srcSizeY, colorA);
    }

    public final void drawText(String content, double posX, double posY, double fontSize, String fontFamily, int colorR, int colorG, int colorB)
    {
        gameView.drawText(content, posX, posY, TextPositioning.RIGHT, fontSize, fontFamily, 255, colorR, colorG, colorB);
    }

    public final void drawText(String content, double posX, double posY, double fontSize, String fontFamily, int colorA, int colorR, int colorG, int colorB)
    {
        gameView.drawText(content, posX, posY, TextPositioning.RIGHT, fontSize, fontFamily, colorA, colorR, colorG, colorB);
    }

    public final void drawText(String content, double posX, double posY, int positioning, double fontSize, String fontFamily, int colorR, int colorG, int colorB)
    {
        gameView.drawText(content, posX, posY, positioning, fontSize, fontFamily, 255, colorR, colorG, colorB);
    }

    public final void drawText(String content, double posX, double posY, int positioning, double fontSize, String fontFamily, int colorA, int colorR, int colorG, int colorB)
    {
        gameView.drawText(content, posX, posY, positioning, fontSize, fontFamily, colorA, colorR, colorG, colorB);
    }

    public final void fill(int colorR, int colorG, int colorB)
    {
        gameView.fill(255, colorR, colorG, colorB);
    }

    public final void fill(int colorA, int colorR, int colorG, int colorB)
    {
        gameView.fill(colorA, colorR, colorG, colorB);
    }

    public final void drawLine(double posX, double posY, double endX, double endY, double width, int colorR, int colorG, int colorB)
    {
        gameView.drawLine(posX, posY, endX, endY, width, 255, colorR, colorG, colorB);
    }

    public final void drawLine(double posX, double posY, double endX, double endY, double width, int colorA, int colorR, int colorG, int colorB)
    {
        gameView.drawLine(posX, posY, endX, endY, width, colorA, colorR, colorG, colorB);
    }

}
