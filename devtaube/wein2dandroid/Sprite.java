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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public final class Sprite implements RenderCalls
{

    private Bitmap bitmap;
    private Canvas canvas;
    private int width;
    private int height;


    public Sprite(int resource)
    {
        Bitmap loadedBitmap = BitmapFactory.decodeResource(Wein2DApplication.WEIN2DAPPLICATION.getResources(), resource);
        bitmap = loadedBitmap.copy(Bitmap.Config.ARGB_8888, true);
        init();
    }

    public Sprite(int width, int height)
    {
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        init();
    }

    private void init()
    {
        this.canvas = new Canvas(bitmap);
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
    }

    public Bitmap getBitmap()
    {
        return bitmap;
    }

    @Override
    public Canvas getGraphics() { return canvas; }

    @Override
    public int getWidth() { return bitmap.getWidth(); }

    @Override
    public int getHeight() { return bitmap.getHeight(); }

    @Override
    public boolean drawingAllowed() { return true; }

}
