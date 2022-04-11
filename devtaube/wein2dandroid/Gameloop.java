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
