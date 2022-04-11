package devtaube.wein2dandroid;

public final class BoxCollision
{

    private BoxCollision() {}

    public static boolean rectTouchingRect(double rect1PosX, double rect1PosY, double rect1SizeX, double rect1SizeY, double rect2PosX, double rect2PosY, double rect2SizeX, double rect2SizeY)
    {
        return (!(rect1PosX + rect1SizeX < rect2PosX)
                && !(rect2PosX + rect2SizeX < rect1PosX)
                && !(rect1PosY + rect1SizeY < rect2PosY)
                && !(rect2PosY + rect2SizeY < rect1PosY));
    }

    public static boolean pointInsideRect(double pointX, double pointY, double rectPosX, double rectPosY, double rectSizeX, double rectSizeY)
    {
        return (rectPosX < pointX
                && pointX < rectPosX + rectSizeX
                && rectPosY < pointY
                && pointY < rectPosY + rectSizeY);
    }

    public static boolean rectInsideRect(double rect1PosX, double rect1PosY, double rect1SizeX, double rect1SizeY, double rect2PosX, double rect2PosY, double rect2SizeX, double rect2SizeY)
    {
        return (rect2PosX < rect1PosX
                && rect1PosX + rect1SizeX < rect2PosX + rect2SizeX
                && rect2PosY < rect1PosY
                && rect1PosY + rect1SizeY < rect2PosY + rect2SizeY);
    }

}
