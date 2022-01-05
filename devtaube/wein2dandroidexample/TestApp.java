package devtaube.wein2dandroidexample;

import devtaube.wein2dandroid.*;

public class TestApp implements Application // implements Application to be usable by wein2dandroid
{
    App app;
    int x = 0;
    public void onCreate(App app) // gets called when wein2dandroid is ready
    {
        this.app = app; // store the app object to use it later
    }
    public void onFrame() // gets called once per frame
    {
        x += 3; // move the ball
        if(app.getMouseL() || x > app.width) // move the ball back if we touch the screen or the ball flies offscreen
            x = -150;
        app.fill(31, 31, 31); // fill the screen with gray
        app.drawOval(x, (app.height - 150) / 2, 150, 150, 125, 125, 255); // draw the ball
    }
}
