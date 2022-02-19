package devtaube.wein2dandroidexample;

import devtaube.wein2dandroid.*;

public class ExampleProgram extends Wein2DApplication { // extend Application

    Counter counter;

    public void onCreate() {
        counter = (Counter) FileIOMethods.deserializeObject("test.ser");
        if(counter == null) counter = new Counter();
        System.out.println(counter.count);
    }

    boolean lastFrameTouch = false;

    public void onFrame() {
        if(!lastFrameTouch && getMouseL())
        {
            counter.incrementCount();
            FileIOMethods.serializeObject(counter, "test.ser");
        }
        lastFrameTouch = getMouseL();
        drawText(String.valueOf(counter.count), width / 2, height / 2 - 50, TextPositioning.CENTER, 100, "Consolas", 255, 255, 255);
    }
}

class Counter implements java.io.Serializable
{
    public int count = 0;

    void incrementCount()
    {
        count++;
    }
}