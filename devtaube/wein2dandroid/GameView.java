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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.text.InputType;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

class GameView extends SurfaceView implements SurfaceHolder.Callback
{

    private Wein2DApplication wein2DApplication;

    KeyInputConnection keyInputConnection = new KeyInputConnection(this, true);

    Canvas currentCanvas;
    int width;
    int height;

    boolean touching;
    int touchX = 0;
    int touchY = 0;

    private boolean keyboardShown;


    public GameView(Wein2DApplication wein2DApplication)
    {
        super(wein2DApplication);
        this.wein2DApplication = wein2DApplication;
        this.getHolder().addCallback(this);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);

        Display display = ((WindowManager) wein2DApplication.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);
        width = displaySize.x;
        height = displaySize.y;
    }


    void showKeyboard(boolean showKeyboard)
    {
        InputMethodManager inputManager = (InputMethodManager) wein2DApplication.getSystemService(Context.INPUT_METHOD_SERVICE);

        if(showKeyboard && !keyboardShown)
        {
            requestFocus();
            inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            keyboardShown = true;
        }

        if(!showKeyboard && keyboardShown)
        {
            inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            keyboardShown = false;
        }
    }


    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs)
    {
        outAttrs.inputType = InputType.TYPE_CLASS_TEXT;
        return keyInputConnection;
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder)
    {
        setWillNotDraw(false);
        setVisibility(View.VISIBLE);

        keyboardShown = false;

        wein2DApplication.surfaceCreated();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder)
    {
        wein2DApplication.surfaceDestroyed();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        touchX = (int) event.getX();
        touchY = (int) event.getY();

        int eventaction = event.getAction();
        switch (eventaction)
        {
            case MotionEvent.ACTION_DOWN:
                touching = true;
                break;

            case MotionEvent.ACTION_UP:
                touching = false;
                break;
        }

        return true;
    }


    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        this.currentCanvas = canvas;

        keyInputConnection.onFrame();

        wein2DApplication.internalOnFrame();
    }

}
