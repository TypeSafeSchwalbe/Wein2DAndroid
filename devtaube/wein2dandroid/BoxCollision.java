/*
 * Copyright (c) 2022, DevTaube
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
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
