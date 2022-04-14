# About
Wein2DAndroid is a library for handling graphics, input and sound for Android apps in Java with very similar method names and structure to Wein2D to allow for easy porting of code across libraries.

## Code Example
This is an Example for a simple App using Wein2DAndroid.

This is the main activity that's defined in the AndroidManifest.xml:
```java
import devtaube.wein2dandroid.*;

public class ExampleProgram extends Wein2DApplication
{

    // cube values (feel free to play with these!)
    static final double CUBE_SIZE = 50.0; // the width and height of the cube (in pixels)
    static final double CUBE_JUMP_VELOCITY = 800.0; // the cube's jump velocity (in pixels per second)
    static final double CUBE_GRAVITATION = 1600.0; // gravitation (how much velocity gets removed per second)
    static final double CUBE_BOUNCEBACK_MULTIPLIER = 0.4; // how much velocity the cube keeps after hitting the ground

    double cubeHeight = 0.0;
    double cubeVelocity = 0.0;

    @Override
    public void onCreate()
    {
    }

    @Override
    public void onFrame()
    {
        // update calls //////////////////////////////////////////////////

        // if screen is tapped, set the cubes velocity (let the cube jump up)
        if(getMouseL()) cubeVelocity = CUBE_JUMP_VELOCITY;

        // move the cube up and down according to it's velocity
        cubeHeight += cubeVelocity * this.deltaTime;
        // if the cube is not on the ground remove some of the cube's velocity (gravitation)
        if(cubeHeight > 0.0) cubeVelocity -= CUBE_GRAVITATION * this.deltaTime;

        // if the cube is below or on the ground, set him onto the ground, invert the cube's velocity (movement) and remove some of it's velocity
        if(cubeHeight <= 0.0) {
            cubeVelocity = -cubeVelocity * CUBE_BOUNCEBACK_MULTIPLIER;
            cubeHeight = 0.0;
        }

        // render calls //////////////////////////////////////////////////

        // fill the screen with blue
        fill(255, 11, 138, 143);

        // draw the cube
        drawRect(
                (this.width - CUBE_SIZE) / 2.0, // draw at the center of the screen (x axis)
                this.height - CUBE_SIZE - cubeHeight, // draw at the cube's height (y axis)
                CUBE_SIZE, CUBE_SIZE, // draw the cube with it's width and height
                255, 255, 255, 255 // draw the cube white
        );
    }

}
```

# Documentation
Documentation for Wein2DAndroid can be found at [https://wein2ddocs.netlify.app](https://wein2ddocs.netlify.app).
