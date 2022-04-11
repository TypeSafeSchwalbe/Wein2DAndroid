package devtaube.wein2dandroid;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

public interface RenderCalls
{

    Canvas getGraphics();

    int getWidth();

    int getHeight();

    boolean drawingAllowed();


    default void drawRectInternal(double posX, double posY, double width, double height, int colorA, int colorR, int colorG, int colorB)
    {
        RenderCallsUtils.DRAWRECTRECT.set((int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posX + width), (int) Math.floor(posY + height));
        RenderCallsUtils.DRAWPAINT.setARGB(colorA, colorR, colorG, colorB);
        getGraphics().drawRect(RenderCallsUtils.DRAWRECTRECT, RenderCallsUtils.DRAWPAINT);
    }

    default void drawOvalInternal(double posX, double posY, double width, double height, int colorA, int colorR, int colorG, int colorB)
    {
        RenderCallsUtils.DRAWPAINT.setARGB(colorA, colorR, colorG, colorB);
        getGraphics().drawOval((int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posX + width), (int) Math.floor(posY + height), RenderCallsUtils.DRAWPAINT);
    }

    default void drawSpriteInternal(Sprite sprite, double posX, double posY, double width, double height, int srcPosX, int srcPosY, int srcWidth, int srcHeight, int colorA)
    {
        RenderCallsUtils.IMAGEALPHAPAINT.setAlpha(colorA);
        RenderCallsUtils.DRAWBITMAPSRCRECT.set(srcPosX, srcPosY, srcPosX + srcWidth, srcPosY + srcHeight);
        RenderCallsUtils.DRAWBITMAPDESTRECT.set((int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posX + width), (int) Math.floor(posY + height));
        getGraphics().drawBitmap(sprite.getBitmap(), RenderCallsUtils.DRAWBITMAPSRCRECT, RenderCallsUtils.DRAWBITMAPDESTRECT, RenderCallsUtils.IMAGEALPHAPAINT);
    }

    default void drawTextInternal(String content, double posX, double posY, int positioning, double fontSize, String fontFamily, int colorA, int colorR, int colorG, int colorB)
    {
        RenderCallsUtils.TEXTPAINT.setARGB(colorA, colorR, colorG, colorB);
        RenderCallsUtils.TEXTPAINT.setTextSize((int) Math.floor(fontSize));
        RenderCallsUtils.TEXTPAINT.setTypeface(Typeface.create(fontFamily, Typeface.NORMAL));
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
        getGraphics().drawText(content, (int) Math.floor(posX), (int) Math.floor(posY + fontSize), RenderCallsUtils.TEXTPAINT);
    }

    default void fillInternal(int colorA, int colorR, int colorG, int colorB)
    {
        getGraphics().drawARGB(colorA, colorR, colorG, colorB);
    }

    default void drawLineInternal(double posX, double posY, double endX, double endY, double lineWidth, int colorA, int colorR, int colorG, int colorB)
    {
        RenderCallsUtils.DRAWPAINT.setARGB(colorA, colorR, colorG, colorB);
        RenderCallsUtils.DRAWPAINT.setStrokeWidth((int) Math.floor(lineWidth));
        getGraphics().drawLine((int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(endX), (int) Math.floor(endY), RenderCallsUtils.DRAWPAINT);
    }

    default void drawVirtualCanvasInternal(VirtualCanvas virtualCanvas, double posX, double posY, double width, double height, int srcPosX, int srcPosY, int srcWidth, int srcHeight, int colorA)
    {
        RenderCallsUtils.IMAGEALPHAPAINT.setAlpha(colorA);
        RenderCallsUtils.DRAWBITMAPSRCRECT.set(srcPosX, srcPosY, srcPosX + srcWidth, srcPosY + srcHeight);
        RenderCallsUtils.DRAWBITMAPDESTRECT.set((int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posX + width), (int) Math.floor(posY + height));
        getGraphics().drawBitmap(virtualCanvas.getBitmap(), RenderCallsUtils.DRAWBITMAPSRCRECT, RenderCallsUtils.DRAWBITMAPDESTRECT, RenderCallsUtils.IMAGEALPHAPAINT);
    }


    default void drawRect(double posX, double posY, double width, double height, int colorR, int colorG, int colorB) { if(drawingAllowed()) drawRectInternal(posX, posY, width, height, 255, colorR, colorG, colorB); }

    default void drawRect(double posX, double posY, double width, double height, int colorA, int colorR, int colorG, int colorB) { if(drawingAllowed()) drawRectInternal(posX, posY, width, height, colorA, colorR, colorG, colorB); }

    default void drawOval(double posX, double posY, double width, double height, int colorR, int colorG, int colorB) { if(drawingAllowed()) drawOvalInternal(posX, posY, width, height, 255, colorR, colorG, colorB); }

    default void drawOval(double posX, double posY, double width, double height, int colorA, int colorR, int colorG, int colorB) { if(drawingAllowed()) drawOvalInternal(posX, posY, width, height, colorA, colorR, colorG, colorB); }

    default void drawSprite(Sprite sprite, double posX, double posY) { if(drawingAllowed()) drawSpriteInternal(sprite, posX, posY, sprite.getWidth() + posX, sprite.getHeight() + posY, 0, 0, sprite.getWidth(), sprite.getHeight(), 255); }

    default void drawSprite(Sprite sprite, double posX, double posY, int colorA) { if(drawingAllowed()) drawSpriteInternal(sprite, posX, posY, sprite.getWidth() + posX, sprite.getHeight() + posY, 0, 0, sprite.getWidth(), sprite.getHeight(), colorA); }

    default void drawSprite(Sprite sprite, double posX, double posY, double width, double height) { if(drawingAllowed()) drawSpriteInternal(sprite, posX, posY, width + posX, height + posY, 0, 0, sprite.getWidth(), sprite.getHeight(), 255); }

    default void drawSprite(Sprite sprite, double posX, double posY, double width, double height, int colorA) { if(drawingAllowed()) drawSpriteInternal(sprite, posX, posY, width + posX, height + posY, 0, 0, sprite.getWidth(), sprite.getHeight(), colorA); }

    default void drawSprite(Sprite sprite, double posX, double posY, double width, double height, int srcPosX, int srcPosY, int srcWidth, int srcHeight) { if(drawingAllowed()) drawSpriteInternal(sprite, posX, posY, width + posX, height + posY, srcPosX, srcPosY, srcWidth + srcPosX, srcHeight + srcPosY, 255); }

    default void drawSprite(Sprite sprite, double posX, double posY, double width, double height, int srcPosX, int srcPosY, int srcWidth, int srcHeight, int colorA) { if(drawingAllowed()) drawSpriteInternal(sprite, posX, posY, width + posX, height + posY, srcPosX, srcPosY, srcWidth + srcPosX, srcHeight + srcPosY, colorA); }

    default void drawText(String content, double posX, double posY, double fontSize, String fontFamily, int colorR, int colorG, int colorB) { if(drawingAllowed()) drawTextInternal(content, posX, posY, TextPositioning.RIGHT, fontSize, fontFamily, 255, colorR, colorG, colorB); }

    default void drawText(String content, double posX, double posY, double fontSize, String fontFamily, int colorA, int colorR, int colorG, int colorB) { if(drawingAllowed()) drawTextInternal(content, posX, posY, TextPositioning.RIGHT, fontSize, fontFamily, colorA, colorR, colorG, colorB); }

    default void drawText(String content, double posX, double posY, int positioning, double fontSize, String fontFamily, int colorR, int colorG, int colorB) { if(drawingAllowed()) drawTextInternal(content, posX, posY, positioning, fontSize, fontFamily, 255, colorR, colorG, colorB); }

    default void drawText(String content, double posX, double posY, int positioning, double fontSize, String fontFamily, int colorA, int colorR, int colorG, int colorB) { if(drawingAllowed()) drawTextInternal(content, posX, posY, positioning, fontSize, fontFamily, colorA, colorR, colorG, colorB); }

    default void fill(int colorR, int colorG, int colorB) { if(drawingAllowed()) fillInternal(255, colorR, colorG, colorB); }

    default void fill(int colorA, int colorR, int colorG, int colorB) { if(drawingAllowed()) fillInternal(colorA, colorR, colorG, colorB); }

    default void drawLine(double posX, double posY, double endX, double endY, double lineWidth, int colorR, int colorG, int colorB) { if(drawingAllowed()) drawLineInternal(posX, posY, endX, endY, lineWidth, 255, colorR, colorG, colorB); }

    default void drawLine(double posX, double posY, double endX, double endY, double lineWidth, int colorA, int colorR, int colorG, int colorB) { if(drawingAllowed()) drawLineInternal(posX, posY, endX, endY, lineWidth, colorA, colorR, colorG, colorB); }

    default void drawVirtualCanvas(VirtualCanvas virtualCanvas, double posX, double posY) { if(drawingAllowed()) drawVirtualCanvasInternal(virtualCanvas, posX, posY, virtualCanvas.getWidth() + posX, virtualCanvas.getHeight() + posY, 0, 0, virtualCanvas.getWidth(), virtualCanvas.getHeight(), 255); }

    default void drawVirtualCanvas(VirtualCanvas virtualCanvas, double posX, double posY, int colorA) { if(drawingAllowed()) drawVirtualCanvasInternal(virtualCanvas, posX, posY, virtualCanvas.getWidth() + posX, virtualCanvas.getHeight() + posY, 0, 0, virtualCanvas.getWidth(), virtualCanvas.getHeight(), colorA); }

    default void drawVirtualCanvas(VirtualCanvas virtualCanvas, double posX, double posY, double width, double height) { if(drawingAllowed()) drawVirtualCanvasInternal(virtualCanvas, posX, posY, width + posX, height + posY, 0, 0, virtualCanvas.getWidth(), virtualCanvas.getHeight(), 255); }

    default void drawVirtualCanvas(VirtualCanvas virtualCanvas, double posX, double posY, double width, double height, int colorA) { if(drawingAllowed()) drawVirtualCanvasInternal(virtualCanvas, posX, posY, width + posX, height + posY, 0, 0, virtualCanvas.getWidth(), virtualCanvas.getHeight(), colorA); }

    default void drawVirtualCanvas(VirtualCanvas virtualCanvas, double posX, double posY, double width, double height, int srcPosX, int srcPosY, int srcWidth, int srcHeight) { if(drawingAllowed()) drawVirtualCanvasInternal(virtualCanvas, posX, posY, width + posX, height + posY, srcPosX, srcPosY, srcWidth + srcPosX, srcHeight + srcPosY, 255); }

    default void drawVirtualCanvas(VirtualCanvas virtualCanvas, double posX, double posY, double width, double height, int srcPosX, int srcPosY, int srcWidth, int srcHeight, int colorA) { if(drawingAllowed()) drawVirtualCanvasInternal(virtualCanvas, posX, posY, width + posX, height + posY, srcPosX, srcPosY, srcWidth + srcPosX, srcHeight + srcPosY, colorA); }

}
