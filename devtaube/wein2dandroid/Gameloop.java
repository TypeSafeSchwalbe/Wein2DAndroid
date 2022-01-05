package wein2dandroid;

import android.view.SurfaceHolder;

class Gameloop extends Thread
{
    // Variables ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private SurfaceHolder surfaceHolder;
    private App app;
    protected boolean running;
    protected int fps;
    // Constructor ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Gameloop(SurfaceHolder surfaceHolder, App app)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.app = app;
        this.fps = 60;
    }
    // Gameloop ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void run()
    {
        running = true;
        long frameStartTime;
        long frameTime = 0;
        long waitTime;
        while(running)
        {
            // get Time at start of Frame
            frameStartTime = System.currentTimeMillis();
            // call methods
            synchronized(surfaceHolder)
            {
                app.postInvalidate();
            }
            // calculate amount of sleep
            waitTime = 1000 / fps - frameTime;
            // sleep
            try
            {
                this.sleep(waitTime);
            }
            catch (Exception e)
            {}
        }
    }
}
