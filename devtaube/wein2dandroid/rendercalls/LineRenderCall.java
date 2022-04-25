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

import devtaube.wein2dandroid.RenderCalls;


public final class LineRenderCall implements RenderCall
{

    private RenderCalls renderCalls;

    private double posX = Double.NaN;
    private double posY = Double.NaN;
    private double endX = Double.NaN;
    private double endY = Double.NaN;

    private double lineWidth = Double.NaN;

    private int colorRed = 0;
    private int colorGreen = 0;
    private int colorBlue = 0;
    private int colorAlpha = 255;

    private boolean invalidated = false;


    public LineRenderCall(RenderCalls renderCalls) { this.renderCalls = renderCalls; }


    public LineRenderCall setStartPosition(double x, double y)
    {
        posX = x;
        posY = y;
        return this;
    }

    public LineRenderCall setEndPosition(double x, double y)
    {
        endX = x;
        endY = y;
        return this;
    }

    public LineRenderCall setWidth(double width)
    {
        lineWidth = width;
        return this;
    }

    public LineRenderCall setColor(int red, int green, int blue)
    {
        colorRed = red;
        colorGreen = green;
        colorBlue = blue;
        return this;
    }

    public LineRenderCall setColor(int red, int green, int blue, int alpha)
    {
        colorRed = red;
        colorGreen = green;
        colorBlue = blue;
        colorAlpha = alpha;
        return this;
    }

    public LineRenderCall setAlpha(int alpha)
    {
        colorAlpha = alpha;
        return this;
    }


    @Override
    public void draw()
    {
        if(!renderCalls.drawingAllowed())
            return;

        if(invalidated)
            throw new RuntimeException("RenderCall has been drawn already. Please use a new one.");

        if(Double.isNaN(posX) || Double.isNaN(posY))
            throw new RuntimeException("LineRenderCall has no start position set. Set it's start position using 'LineRenderCall#setStartPosition'.");

        if(Double.isNaN(endX) || Double.isNaN(endY))
            throw new RuntimeException("LineRenderCall has no end position set. Set it's end position using 'LineRenderCall#setEndPosition'.");

        if(Double.isNaN(lineWidth))
            throw new RuntimeException("LineRenderCall has no width set. Set it's width using 'LineRenderCall#setWidth'.");

        RenderCallsUtils.DRAWPAINT.setARGB(colorAlpha, colorRed, colorGreen, colorBlue);
        RenderCallsUtils.DRAWPAINT.setStrokeWidth((int) Math.floor(lineWidth));
        renderCalls.getGraphics().drawLine((int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(endX), (int) Math.floor(endY), RenderCallsUtils.DRAWPAINT);

        invalidated = true;
    }

}
