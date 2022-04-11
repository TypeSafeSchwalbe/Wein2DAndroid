package devtaube.wein2dandroid;

import android.app.Activity;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public abstract class Wein2DApplication extends Activity implements RenderCalls
{

    static Wein2DApplication WEIN2DAPPLICATION;

    private GameView gameView;
    private Gameloop gameloop;

    public int width;
    public int height;
    public double deltaTime;

    private int fps;

    private boolean surfaceCreated = false;


    public abstract void onCreate();

    public abstract void onFrame();


    void internalOnFrame()
    {
        width = gameView.width;
        height = gameView.height;
        deltaTime = gameloop.getDeltaTime();

        onFrame();
    }


    public final void setTargetedFPS(int fps)
    {
        this.fps = fps;
        if(gameloop != null)
            gameloop.setFPS(fps);
    }

    public final void showKeyboard(boolean showKeyboard) { gameView.showKeyboard(showKeyboard); }


    public final int getMouseX() { return gameView.touchX; }

    public final int getMouseY() { return gameView.touchY; }

    public final boolean getMouseL() { return gameView.touching; }

    public final int getFPS()
    {
        if(gameloop != null)
            return gameloop.getFPS();
        return 0;
    }

    public final String getTypedText() { return gameView.keyInputConnection.getTypedText(); }

    public final void setTypedText(String text) { gameView.keyInputConnection.setTypedText(text); }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        WEIN2DAPPLICATION = this;

        gameView = new GameView(this);

        this.setContentView(gameView);
    }


    @Override
    public final Canvas getGraphics() { return gameView.currentCanvas; }

    @Override
    public final int getWidth() { return gameView.width; }

    @Override
    public final int getHeight() { return gameView.height; }

    @Override
    public final boolean drawingAllowed() { return true; }


    void surfaceCreated()
    {
        if(!surfaceCreated)
        {
            onCreate();
            surfaceCreated = true;
        }

        gameloop = new Gameloop(gameView);
        gameloop.setFPS(fps);
        gameloop.start();
    }

    void surfaceDestroyed()
    {
        gameloop.stopLoop();

        try
        {
            gameloop.join();
        }
        catch (Exception e) { e.printStackTrace(); }
    }

}
