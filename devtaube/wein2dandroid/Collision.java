package wein2dandroid;

public class Collision
{
    // Line touching Rect ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static boolean lineTouchingRect(int lineX, int lineY, int lineLength, int rectPosX, int rectPosY, int rectSizeX, int rectSizeY)
    {
        return (rectPosX < lineX + lineLength
                && lineX < rectPosX + rectSizeX
                && rectPosY < lineY
                && lineY < rectPosY + rectSizeY);
    }
    // Rect touching Rect ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static boolean rectTouchingRect(int rect1PosX, int rect1PosY, int rect1SizeX, int rect1SizeY, int rect2PosX, int rect2PosY, int rect2SizeX, int rect2SizeY)
    {
        return (!(rect1PosX + rect1SizeX < rect2PosX)
                && !(rect2PosX + rect2SizeX < rect1PosX)
                && !(rect1PosY + rect1SizeY < rect2PosY)
                && !(rect2PosY + rect2SizeY < rect1PosY));
    }
    // Point inside Rect ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static boolean pointInsideRect(int pointX, int pointY, int rectPosX, int rectPosY, int rectSizeX, int rectSizeY)
    {
        return (rectPosX < pointX
                && pointX < rectPosX + rectSizeX
                && rectPosY < pointY
                && pointY < rectPosY + rectSizeY);
    }
    // Line inside Rect ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static boolean lineInsideRect(int lineX, int lineY, int lineLength, int rectPosX, int rectPosY, int rectSizeX, int rectSizeY)
    {
        return (rectPosX < lineX
                && lineX + lineLength < rectPosX + rectSizeX
                && rectPosY < lineY
                && lineY < rectPosY + rectSizeY);
    }
    // Rect inside Rect ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static boolean rectInsideRect(int rect1PosX, int rect1PosY, int rect1SizeX, int rect1SizeY, int rect2PosX, int rect2PosY, int rect2SizeX, int rect2SizeY)
    {
        return (rect2PosX < rect1PosX
                && rect1PosX + rect1SizeX < rect2PosX + rect2SizeX
                && rect2PosY < rect1PosY
                && rect1PosY + rect1SizeY < rect2PosY + rect2SizeY);
    }
}
