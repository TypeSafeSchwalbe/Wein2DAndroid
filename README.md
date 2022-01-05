# Introduction
Wein2DAndroid is a collection of java classes to speed up the process of making a game in java for android. Its methods and classes are based on [Wein2D](https://www.github.com/devtaube/wein2d).

## Code Example
This is an Example for a simple App using Wein2DAndroid.

MainActivity.java (the main activity that's defined in the AndroidManifest.xml):
```java
package devtaube.wein2dandroidexample;

import devtaube.wein2dandroid.App;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // call superclass and set flags
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // set content view to instance of wein2dandroid.App and pass its constructor this as well as a new instance of the TestApp class (needs to implement Application)
        this.setContentView(new App(this, new TestApp()));
    }
}
```

TestApp.java:
```java
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
```
# Documentation
This is a list of all features, classes and methods.

## Application
-> interface

Methods:
 - void onCreate(wein2dandroid.App app) >> gets called by wein2dandroid when the constructor for the app object has finished
 - void onFrame() >> gets called once per frame by wein2dandroid

## App
Constructor:
App(Android.content.Context context, wein2dandroid.Application application)

Methods:
- Setters
    - void setFPS(int fps) >> changes the gameloop's targeted FPS
- Getters
    - int getWidth() >> returns the viewport's width
    - int getHeight() >> returns the viewport's height
    - boolean getMouseL() >> returns if the screen is being touched
    - int getMouseX() >> returns the position of the latest touch on the x axis.
    - int getMouseY() >> returns the position of the latest touch on the y axis.
- Drawing stuff on screen
    - void addGameloop(Gameloop gameloop) >> add an object to the window that implements the "Gameloop" interface
   - void startGameloop() >> starts the gameloop if a Gameloop-object is added
   - void setFPS(int fps) >> configures the gameloop to target the passed fps if a Gameloop-object is added
- Drawing stuff on screen
   - void drawRect(int posX, int posY, int sizeX, int sizeY, int colorR, int colorG, int colorB) >> draw rectangle
   - void drawRect(int posX, int posY, int sizeX, int sizeY, int colorA, int colorR, int colorG, int colorB) >> draw rectangle (with alpha)
   - void drawOval(int posX, int posY, int sizeX, int sizeY, int colorR, int colorG, int colorB) >> draw oval
   - void drawOval(int posX, int posY, int sizeX, int sizeY, int colorA, int colorR, int colorG, int colorB) >> draw oval (with alpha)
   - void drawSprite(Sprite sprite, int posX, int posY) >> draw sprite
   - void drawSprite(Sprite sprite, int posX, int posY, int colorA) >> draw sprite (with alpha)
   - void drawSprite(Sprite sprite, int posX, int posY, int sizeX, int sizeY) >> draw sprite (specified size)
   - void drawSprite(Sprite sprite, int posX, int posY, int sizeX, int sizeY, int colorA) >> draw sprite (specified size, with alpha)
   - void drawSprite(Sprite sprite, int posX, int posY, int sizeX, int sizeY, int srcPosX, int srcPosY, int srcSizeX, int srcSizeY) >> draw sprite (specified size and source size)
   - void drawSprite(Sprite sprite, int posX, int posY, int sizeX, int sizeY, int srcPosX, int srcPosY, int srcSizeX, int srcSizeY, int colorA) >> draw sprite (specified size and source size, with alpha)
   - void drawText(String content, int posX, int posY, int fontSize, String fontFamily, int colorR, int colorG, int colorB) >> draw text
   - void drawText(String content, int posX, int posY, int fontSize, String fontFamily, int colorA, int colorR, int colorG, int colorB) >> draw text (with alpha)
   - void drawText(String content, int posX, int posY, String positioning, int fontSize, String fontFamily, int colorR, int colorG, int colorB) >> draw text (with positioning)
        - positioning may be: "LEFT", "CENTER", "RIGHT"
   - void drawText(String content, int posX, int posY, String positioning, int fontSize, String fontFamily, int colorA, int colorR, int colorG, int colorB) >> draw text (with positioning, with alpha)
        - positioning may be: "LEFT", "CENTER", "RIGHT"
   - void fill(int colorR, int colorG, int colorB) >> fill window with color
   - void fill(int colorA, int colorR, int colorG, int colorB) >> fill window with color (with alpha)

Global Variables:
    - int width >> stores the width of the screen [DIFFERENT TO WEIN2D: WEIN2D USES WINDOW.GETWIDTH()!]
    - int height >> stores the height of the screen [DIFFERENT TO WEIN2D: WEIN2D USES WINDOW.GETHEIGHT()!]

## Sprite
Constructor:
Sprite(int resource (for example 'R.drawable.test_image'))
-> creates and loads the Sprite from the given Path

## Sound
Constructor:
Sound(int resource (for example 'R.raw.test_sound'))
-> creates and loads the Sound from the given Path

Methods:
 - void play() >> plays the sound
 - void stop() >> stops playback of the sound

## Collision
Methods:
 - static boolean lineTouchingRect(int lineX, int lineY, int lineLength, int rectPosX, int rectPosY, int rectSizeX, int rectSizeY)
     - returns 'false' if line doesn't touch specified rectangle
     - returns 'true' if line touches specified rectangle
 - static boolean rectTouchingRect(int rect1PosX, int rect1PosY, int rect1SizeX, int rect1SizeY, int rect2PosX, int rect2PosY, int rect2SizeX, int rect2SizeY)
     - returns 'false' if rectangle doesn't touch specified rectangle
     - returns 'true' if rectangle touches specified rectangle
 - static boolean pointInsideRect(int pointX, int pointY, int rectPosX, int rectPosY, int rectSizeX, int rectSizeY)
     - returns 'false' if point isn't inside specified rectangle
     - returns 'true' if point is inside specified rectangle
 - static boolean lineInsideRect(int lineX, int lineY, int lineLength, int rectPosX, int rectPosY, int rectSizeX, int rectSizeY)
     - returns 'false' if line isn't inside specified rectangle
     - returns 'true' if line is inside specified rectangle
 - static boolean rectInsideRect(int rect1PosX, int rect1PosY, int rect1SizeX, int rect1SizeY, int rect2PosX, int rect2PosY, int rect2SizeX, int rect2SizeY)
     - returns 'false' if rectangle isn't inside specified rectangle
     - returns 'true' if rectangle is inside specified rectangle
