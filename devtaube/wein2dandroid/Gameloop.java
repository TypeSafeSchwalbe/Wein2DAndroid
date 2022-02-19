package devtaube.wein2dandroid;

import android.view.SurfaceHolder;

class Gameloop extends Thread
{
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    protected boolean running;
    protected int fps;
    private int averageFrames = 0;

    public Gameloop(GameView gameView)
    {
        this.surfaceHolder = gameView.getHolder();
        this.gameView = gameView;
        this.fps = 60;
    }

    protected int getFPS()
    {
        return averageFrames;
    }

    @Override
    public void run()
    {
        running = true;
        long frameStartTime = 0;
        long frameTime;
        long waitTime;
        int framesCounter = 0;
        long lastFrameCountResetTime = 0;
        while(running)
        {
            if(fps > 0) // is fps limited?
            {
                // get Time at start of Frame
                frameStartTime = System.currentTimeMillis();
            }
            // call methods
            synchronized(surfaceHolder)
            {
                gameView.postInvalidate();
            }
            // calculate FPS
            if(lastFrameCountResetTime + 1000 < System.currentTimeMillis())
            {
                lastFrameCountResetTime = System.currentTimeMillis();
                averageFrames = framesCounter;
                framesCounter = 0;
            }
            framesCounter++;
            // Wait
            if(fps > 0 && running) // is fps limited?
            {
                // calculate amount of sleep
                frameTime = System.currentTimeMillis() - frameStartTime;
                waitTime = 1000 / fps - frameTime;
                // sleep
                if(waitTime > 0)
                {
                    try {
                        Thread.sleep(waitTime);
                    } catch (Exception e) { e.printStackTrace(); }
                }
            }
        }
    }
}
