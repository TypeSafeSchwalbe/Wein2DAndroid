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

import android.graphics.Canvas;

import devtaube.wein2dandroid.rendercalls.*;

public interface RenderCalls
{

    Canvas getGraphics();

    int getWidth();

    int getHeight();

    boolean drawingAllowed();


    default RectangleRenderCall drawRectangle() { return new RectangleRenderCall(this); }

    default OvalRenderCall drawOval() { return new OvalRenderCall(this); }

    default SpriteRenderCall drawSprite() { return new SpriteRenderCall(this); }

    default TextRenderCall drawText() { return new TextRenderCall(this); }

    default LineRenderCall drawLine() { return new LineRenderCall(this); }

    default ClearRectangleRenderCall clearRectangle() { return new ClearRectangleRenderCall(this); }

    default void fill(int red, int green, int blue) { drawRectangle().setPosition(0, 0).setSize(getWidth(), getHeight()).setColor(red, green, blue).draw(); }

    default void fill(int red, int green, int blue, int alpha) { drawRectangle().setPosition(0, 0).setSize(getWidth(), getHeight()).setColor(red, green, blue, alpha).draw(); }

}
