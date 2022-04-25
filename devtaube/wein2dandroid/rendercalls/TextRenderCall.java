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

import android.graphics.Paint;
import android.graphics.Typeface;

import devtaube.wein2dandroid.RenderCalls;
import devtaube.wein2dandroid.TextPositioning;

public final class TextRenderCall implements RenderCall
{

    private RenderCalls renderCalls;

    private String content;

    private double posX = Double.NaN;
    private double posY = Double.NaN;
    private double size = Double.NaN;

    private int positioning = TextPositioning.RIGHT;

    private String family = "Consolas";

    private int colorRed = 0;
    private int colorGreen = 0;
    private int colorBlue = 0;
    private int colorAlpha = 255;

    private boolean invalidated = false;


    public TextRenderCall(RenderCalls renderCalls) { this.renderCalls = renderCalls; }


    public TextRenderCall setContent(String content)
    {
        this.content = content;
        return this;
    }

    public TextRenderCall setPosition(double x, double y)
    {
        posX = x;
        posY = y;
        return this;
    }

    public TextRenderCall setSize(double size)
    {
        this.size = size;
        return this;
    }

    public TextRenderCall setPositioning(int textPositioning)
    {
        positioning = textPositioning;
        return this;
    }

    public TextRenderCall setFontFamily(String fontFamily)
    {
        family = fontFamily;
        return this;
    }

    public TextRenderCall setColor(int red, int green, int blue)
    {
        colorRed = red;
        colorGreen = green;
        colorBlue = blue;
        return this;
    }

    public TextRenderCall setColor(int red, int green, int blue, int alpha)
    {
        colorRed = red;
        colorGreen = green;
        colorBlue = blue;
        colorAlpha = alpha;
        return this;
    }

    public TextRenderCall setAlpha(int alpha)
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

        if(content == null)
            throw new RuntimeException("TextRenderCall has no content set. Set it's content using 'TextRenderCall#setContent'.");

        if(Double.isNaN(posX) || Double.isNaN(posY))
            throw new RuntimeException("TextRenderCall has no position set. Set it's position using 'TextRenderCall#setPosition'.");

        if(Double.isNaN(size))
            throw new RuntimeException("TextRenderCall has no size set. Set it's size using 'TextRenderCall#setSize'.");

        RenderCallsUtils.TEXTPAINT.setARGB(colorAlpha, colorRed, colorGreen, colorBlue);
        RenderCallsUtils.TEXTPAINT.setTextSize((int) Math.floor(size));
        RenderCallsUtils.TEXTPAINT.setTypeface(Typeface.create(family, Typeface.NORMAL));
        RenderCallsUtils.TEXTPAINT.setTextAlign(Paint.Align.LEFT);
        switch(positioning)
        {
            case TextPositioning.LEFT:
                RenderCallsUtils.TEXTPAINT.setTextAlign(Paint.Align.RIGHT);
                break;
            case TextPositioning.CENTER:
                RenderCallsUtils.TEXTPAINT.setTextAlign(Paint.Align.CENTER);
                break;
        }
        renderCalls.getGraphics().drawText(content, (int) Math.floor(posX), (int) Math.floor(posY + size), RenderCallsUtils.TEXTPAINT);

        invalidated = true;
    }

}
