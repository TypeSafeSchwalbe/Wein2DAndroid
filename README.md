# Introduction
Wein2DAndroid is a collection of java classes to speed up the process of making a game in java for android. It supports / has:
- 'onFrame'-method (Gameloop is done for you)
- drawing simple shapes and images to the screen, including text   
- getting touch input from your user   
- playing sounds   
- simple methods for detecting collision   

Other Versions:  
[Wein2D](https://www.github.com/devtaube/wein2d)  
[Wein2D.js](https://www.github.com/devtaube/wein2d.js)  

## Code Example
This is an Example for a simple App using Wein2DAndroid.

This is the main activity that's defined in the AndroidManifest.xml:
```java
import devtaube.wein2dandroid.*;

public class ExampleProgram extends Wein2DApplication { // extend Application
    double ballX = -0.05; // stores where ball is on the x axis

    public void onCreate() {}

    public void onFrame() {
        // calculating
        ballX += 0.2 * deltaTime; // move ball by 1/5 of the screen with per second
        if (ballX > 1.05) ballX = -0.05; // move ball to left if out of screen on the right
        double ballYOffset = Math.sin((ballX * 360 * 2) * Math.PI / 180);
        // rendering
        this.fill(40, 40, 40);
        this.drawOval(
                ballX * width - 25, (height / 2 - 25) + (ballYOffset * height / 4), // posX, posY
                50, 50, // sizeX, sizeY
                (int) ((ballYOffset + 1) / 2 * 255), // red
                150 - (int) ((ballYOffset + 1) / 2 * 150) + 105, // green
                (int) ((ballYOffset + 1) / 2 * 150) + 105 // blue
        );
    }
}
```

# Documentation
This is a list of all features, classes and methods.

## Wein2DApplication (abstract class)

Abstract Methods:
- abstract void onCreate() >> gets called on creation
- abstract void onFrame() >> gets called once per frame

Methods:
- Getters
   - int getFPS() >> returns the number of frames the gameloop did in the last second (value refreshes once per second), returns -1 if no gameloop object configured, returns 0 if no full second passed yet
- Setters
   - final void setTargetedFPS(int fps) >> set the amount of frames per second the gameloop targets (a value below 1 or above 1000 means unlimited) (60 by default)
- Drawing stuff
   - final void drawRect(double posX, double posY, double sizeX, double sizeY, int colorR, int colorG, int colorB) >> draw rectangle
   - final void drawRect(double posX, double posY, double sizeX, double sizeY, int colorA, int colorR, int colorG, int colorB) >> draw rectangle (with alpha)
   - final void drawOval(double posX, double posY, double sizeX, double sizeY, int colorR, int colorG, int colorB) >> draw oval
   - final void drawOval(double posX, double posY, double sizeX, double sizeY, int colorA, int colorR, int colorG, int colorB) >> draw oval (with alpha)
   - final void drawSprite(Sprite sprite, double posX, double posY) >> draw sprite
   - final void drawSprite(Sprite sprite, double posX, double posY, int colorA) >> draw sprite (with alpha)
   - final void drawSprite(Sprite sprite, double posX, double posY, double sizeX, double sizeY) >> draw sprite (specified size)
   - final void drawSprite(Sprite sprite, double posX, double posY, double sizeX, double sizeY, int colorA) >> draw sprite (specified size, with alpha)
   - final void drawSprite(Sprite sprite, double posX, double posY, double sizeX, double sizeY, int srcPosX, int srcPosY, int srcSizeX, int srcSizeY) >> draw sprite (specified size and source size)
   - final void drawSprite(Sprite sprite, double posX, double posY, double sizeX, double sizeY, int srcPosX, int srcPosY, int srcSizeX, int srcSizeY, int colorA) >> draw sprite (specified size and source size, with alpha)
   - final void drawText(String content, double posX, double posY, double fontSize, String fontFamily, int colorR, int colorG, int colorB) >> draw text
   - final void drawText(String content, double posX, double posY, double fontSize, String fontFamily, int colorA, int colorR, int colorG, int colorB) >> draw text (with alpha)
   - final void drawText(String content, double posX, double posY, int positioning, double fontSize, String fontFamily, int colorR, int colorG, int colorB) >> draw text (with positioning)
        - positioning may be: TextPositioning.LEFT, TextPositioning.CENTER, TextPositioning.RIGHT
   - final void drawText(String content, double posX, double posY, int positioning, double fontSize, String fontFamily, int colorA, int colorR, int colorG, int colorB) >> draw text (with positioning, with alpha)
        - positioning may be: TextPositioning.LEFT, TextPositioning.CENTER, TextPositioning.RIGHT
   - final void fill(int colorR, int colorG, int colorB) >> fill window with color
   - final void fill(int colorA, int colorR, int colorG, int colorB) >> fill window with color (with alpha)
   - final void drawLine(double posX, double posY, double endX, double endY, double width, int colorR, int colorG, int colorB) >> draws a line on screen
   - final void drawLine(double posX, double posY, double endX, double endY, double width, int colorA, int colorR, int colorG, int colorB) >> draws a line on screen with alpha
- Input
   - final int getMouseX() >> returns the mouse's position on the x-axis
   - final int getMouseY() >> returns the mouse's position on the y-axis
   - final boolean getMouseL() >> returns if the mouse's left button is being pressed

Variables:  
- int width >> stores the current width of the window
- int height >> stores the current height of the window
- int deltaTime >> time in seconds since last frame

## Sprite
Constructors:  

Sprite(ANDROID RESOURCE)  
-> creates and loads the Sprite from the given resource  

Methods:
 - final int getWidth() >> returns the sprite's width
 - final int getHeight() >> returns the sprite's height

## Sound
Constructors:  

Sound(ANDROID RESOURCE)  
-> creates and loads the sound from the given resource  

Methods:
 - final void play() >> plays the sound  
 - final void stop() >> stops playback of the sound  

## Collision
Methods:
 - static boolean lineTouchingRect(int lineX, int lineY, int lineLengthOnXAxis, int rectPosX, int rectPosY, int rectSizeX, int rectSizeY)
     - returns 'false' if line doesn't touch specified rectangle
     - returns 'true' if line touches specified rectangle
 - static boolean rectTouchingRect(int rect1PosX, int rect1PosY, int rect1SizeX, int rect1SizeY, int rect2PosX, int rect2PosY, int rect2SizeX, int rect2SizeY)
     - returns 'false' if rectangle doesn't touch specified rectangle
     - returns 'true' if rectangle touches specified rectangle
 - static boolean pointInsideRect(int pointX, int pointY, int rectPosX, int rectPosY, int rectSizeX, int rectSizeY)
     - returns 'false' if point isn't inside specified rectangle
     - returns 'true' if point is inside specified rectangle
 - static boolean lineInsideRect(int lineX, int lineY, int lineLengthOnXAxis, int rectPosX, int rectPosY, int rectSizeX, int rectSizeY)
     - returns 'false' if line isn't inside specified rectangle
     - returns 'true' if line is inside specified rectangle
 - static boolean rectInsideRect(int rect1PosX, int rect1PosY, int rect1SizeX, int rect1SizeY, int rect2PosX, int rect2PosY, int rect2SizeX, int rect2SizeY)
     - returns 'false' if rectangle isn't inside specified rectangle
     - returns 'true' if rectangle is inside specified rectangle

## FileIOMethods
Methods:
 - static void serializeObject(Object object, String filePath) >> stores the given object as a file at the given location (Warning: Directories aren't supported!)
 - static Object deserializeObject(String filePath) >> reads an object from the given location and returns it (Warning: Directories aren't supported!)
