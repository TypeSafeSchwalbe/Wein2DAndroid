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

import android.view.SurfaceView;

class Gameloop extends Thread
{

    private SurfaceView surfaceView;
    private int fps;

    private double deltaTime;
    private int fpsCount;
    private boolean running;

    Gameloop(SurfaceView surfaceView)
    {
        this.surfaceView = surfaceView;
        this.fps = 50;
    }

    void setFPS(int fps)
    {
        if(fps <= 0)
            throw new IllegalArgumentException("Targeted FPS cannot be lower than / equal to 0");

        this.fps = fps;
    }

    int getFPS()
    {
        return fpsCount;
    }

    int getTargetedFPS() { return fps; }

    void resetDeltaTime() { deltaTime = 0; }

    double getDeltaTime()
    {
        return deltaTime;
    }

    void stopLoop() { running = false; }

    @Override
    public void run()
    {

        running = true;

        int frameCount = 0;
        long lastFrameCountReset = System.currentTimeMillis();
        long lastFrameTime = System.nanoTime();

        while(running)
        {
            long frameStartTime = System.currentTimeMillis();

            long time = System.nanoTime();
            deltaTime = (double) (time - lastFrameTime) / 1_000_000_000;
            lastFrameTime = time;

            synchronized(surfaceView.getHolder())
            {
                surfaceView.postInvalidate();
            }

            frameCount++;
            if(lastFrameCountReset + 1000 < System.currentTimeMillis())
            {
                fpsCount = frameCount;
                frameCount = 0;
            }

            try
            {
                Thread.sleep(1_000 / fps - (frameStartTime - System.currentTimeMillis()));
            }
            catch(Exception ignored) {}
        }
    }

}
