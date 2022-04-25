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
package devtaube.wein2dandroid.rendercalls;

import android.graphics.Canvas;

import devtaube.wein2dandroid.RenderCalls;
import devtaube.wein2dandroid.Sprite;

public final class SpriteRenderCall implements RenderCall
{

    private RenderCalls renderCalls;

    private Sprite sprite;

    private double posX = Double.NaN;
    private double posY = Double.NaN;
    private double width = Double.NaN;
    private double height = Double.NaN;

    private double srcCutoutX = Double.NaN;
    private double srcCutoutY = Double.NaN;
    private double srcCutoutWidth = Double.NaN;
    private double srcCutoutHeight = Double.NaN;

    private int colorAlpha = 255;

    private double angle;
    private double rotationPointY = Double.NaN;
    private double rotationPointX = Double.NaN;

    private boolean invalidated = false;


    public SpriteRenderCall(RenderCalls renderCalls) { this.renderCalls = renderCalls; }


    public SpriteRenderCall setSprite(Sprite sprite)
    {
        this.sprite = sprite;
        return this;
    }

    public SpriteRenderCall setPosition(double x, double y)
    {
        posX = x;
        posY = y;
        return this;
    }

    public SpriteRenderCall setSize(double width, double height)
    {
        this.width = width;
        this.height = height;
        return this;
    }

    public SpriteRenderCall setSpriteCutoutDimensions(int x, int y, int width, int height)
    {
        this.srcCutoutX = x;
        this.srcCutoutY = y;
        this.srcCutoutWidth = width;
        this.srcCutoutHeight = height;
        return this;
    }

    public SpriteRenderCall setAlpha(int alpha)
    {
        this.colorAlpha = alpha;
        return this;
    }

    public SpriteRenderCall rotateDegrees(double degreesAngle)
    {
        angle += degreesAngle;
        return this;
    }

    public SpriteRenderCall rotateDegrees(double degreesAngle, double rotationCenterX, double rotationCenterY)
    {
        angle += degreesAngle;
        rotationPointX = rotationCenterX;
        rotationPointY = rotationCenterY;
        return this;
    }

    public SpriteRenderCall rotateRadians(double radiansAngle) { return rotateDegrees(Math.toDegrees(radiansAngle)); }

    public SpriteRenderCall rotateRadians(double radiansAngle, double rotationCenterX, double rotationCenterY) { return rotateDegrees(Math.toDegrees(radiansAngle), rotationCenterX, rotationCenterY); }


    @Override
    public void draw()
    {
        if(!renderCalls.drawingAllowed())
            return;

        if(invalidated)
            throw new RuntimeException("RenderCall has been drawn already. Please use a new one.");

        if(sprite == null)
            throw new RuntimeException("SpriteRenderCall has no sprite set. Set it's sprite using 'SpriteRenderCall#setSprite'.");

        if(Double.isNaN(posX) || Double.isNaN(posY))
            throw new RuntimeException("SpriteRenderCall has no position set. Set it's position using 'SpriteRenderCall#setPosition'.");

        if(Double.isNaN(width) || Double.isNaN(height))
            throw new RuntimeException("SpriteRenderCall has no size set. Set it's size using 'SpriteRenderCall#setSize'.");

        Canvas canvas = renderCalls.getGraphics();
        canvas.save();

        if(angle != 0)
        {
            if(Double.isNaN(rotationPointX) || Double.isNaN(rotationPointY))
                canvas.rotate((float) angle, (float) (posX + width / 2.0), (float) (posY + height / 2.0));
            else
                canvas.rotate((float) angle, (float) rotationPointX, (float) rotationPointY);
        }

        if(Double.isNaN(srcCutoutX) || Double.isNaN(srcCutoutY) || Double.isNaN(srcCutoutWidth) || Double.isNaN(srcCutoutHeight))
        {
            srcCutoutX = 0;
            srcCutoutY = 0;
            srcCutoutWidth = sprite.getWidth();
            srcCutoutHeight = sprite.getHeight();
        }

        RenderCallsUtils.IMAGEALPHAPAINT.setAlpha(colorAlpha);
        RenderCallsUtils.DRAWBITMAPSRCRECT.set((int) srcCutoutX, (int) srcCutoutY, (int) (srcCutoutX + srcCutoutWidth), (int) (srcCutoutY + srcCutoutHeight));
        RenderCallsUtils.DRAWBITMAPDESTRECT.set((int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posX + width), (int) Math.floor(posY + height));
        renderCalls.getGraphics().drawBitmap(sprite.getBitmap(), RenderCallsUtils.DRAWBITMAPSRCRECT, RenderCallsUtils.DRAWBITMAPDESTRECT, RenderCallsUtils.IMAGEALPHAPAINT);

        canvas.restore();

        invalidated = true;
    }

}
