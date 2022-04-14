/*
 * Copyright (c) 2022, DevTaube
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package devtaube.wein2dandroid;

import android.app.Activity;
import android.graphics.Canvas;
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

    private int fps = 50;

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
