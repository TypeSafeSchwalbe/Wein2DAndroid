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

public final class ClearRectangleRenderCall implements RenderCall
{

    private RenderCalls renderCalls;

    private double posX = Double.NaN;
    private double posY = Double.NaN;
    private double width = Double.NaN;
    private double height = Double.NaN;

    private double angle;
    private double rotationPointY = Double.NaN;
    private double rotationPointX = Double.NaN;

    private boolean invalidated = false;


    public ClearRectangleRenderCall(RenderCalls renderCalls)
    {
        this.renderCalls = renderCalls;
    }


    public ClearRectangleRenderCall setPosition(double x, double y)
    {
        posX = x;
        posY = y;
        return this;
    }

    public ClearRectangleRenderCall setSize(double width, double height)
    {
        this.width = width;
        this.height = height;
        return this;
    }

    public ClearRectangleRenderCall rotateDegrees(double degreesAngle)
    {
        angle += degreesAngle;
        return this;
    }

    public ClearRectangleRenderCall rotateDegrees(double degreesAngle, double rotationCenterX, double rotationCenterY)
    {
        angle += degreesAngle;
        rotationPointX = rotationCenterX;
        rotationPointY = rotationCenterY;
        return this;
    }

    public ClearRectangleRenderCall rotateRadians(double radiansAngle) { return rotateDegrees(Math.toDegrees(radiansAngle)); }

    public ClearRectangleRenderCall rotateRadians(double radiansAngle, double rotationCenterX, double rotationCenterY) { return rotateDegrees(Math.toDegrees(radiansAngle), rotationCenterX, rotationCenterY); }

    @Override
    public void draw()
    {
        if(!renderCalls.drawingAllowed())
            return;

        if(invalidated)
            throw new RuntimeException("ClearRectangleRenderCall has been drawn already. Please use a new one.");

        if(Double.isNaN(posX) || Double.isNaN(posY))
            throw new RuntimeException("ClearRectangleRenderCall has no position set. Set it's position using 'ClearRectangleRenderCall#setPosition'.");

        if(Double.isNaN(width) || Double.isNaN(height))
            throw new RuntimeException("ClearRectangleRenderCall has no size set. Set it's size using 'ClearRectangleRenderCall#setSize'.");

        /*
        Graphics2D graphics = renderCalls.getGraphics();
        AffineTransform originalTransform = graphics.getTransform();

        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OUT));
        graphics.setColor(new Color(0, 0, 0, 0));

        if(angle != 0)
        {
            double angleSin = Math.abs(Math.sin(angle));
            double angleCos = Math.abs(Math.cos(angle));

            double rotatedWidth = Math.floor(width * angleCos + height * angleSin);
            double rotatedHeight = Math.floor(height * angleCos + width * angleSin);

            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

            if(Double.isNaN(rotationPointX) || Double.isNaN(rotationPointY))
                graphics.rotate(angle, posX + width / 2.0, posY + height / 2.0);
            else
                graphics.rotate(angle, rotationPointX, rotationPointY);
        }

        graphics.fillRect((int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(width), (int) Math.floor(height));

        graphics.setTransform(originalTransform);
         */

        Canvas canvas = renderCalls.getGraphics();
        canvas.save();

        if(angle != 0)
        {
            if(Double.isNaN(rotationPointX) || Double.isNaN(rotationPointY))
                canvas.rotate((float) angle, (float) (posX + width / 2.0), (float) (posY + height / 2.0));
            else
                canvas.rotate((float) angle, (float) rotationPointX, (float) rotationPointY);
        }

        RenderCallsUtils.DRAWRECTRECT.set((int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posX + width), (int) Math.floor(posY + height));
        renderCalls.getGraphics().drawRect(RenderCallsUtils.DRAWRECTRECT, RenderCallsUtils.CLEARRECTPAINT);

        canvas.restore();

        invalidated = true;
    }

}
