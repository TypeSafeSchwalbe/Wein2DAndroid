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

import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.view.inputmethod.BaseInputConnection;

class KeyInputConnection extends BaseInputConnection
{

    private SpannableStringBuilder editable;
    private String lastAccessString = "";
    private String typedText = "";

    KeyInputConnection(GameView view, boolean fullEditor)
    {
        super(view, fullEditor);
        editable = new SpannableStringBuilder();
    }

    void onFrame()
    {
        String currentString = editable.toString();

        if(currentString.length() > lastAccessString.length())
        {
            try
            {
                typedText += currentString.substring(lastAccessString.length()).replaceAll(System.lineSeparator(), "").replaceAll("\n", "");
            }
            catch(Exception ignored) {}
        }

        if(currentString.length() < lastAccessString.length())
        {
            try
            {
                typedText = typedText.substring(0, typedText.length() - (lastAccessString.length() - currentString.length()));
            }
            catch(Exception ignored) {}
        }

        if(typedText.length() >= 1000)
            typedText = typedText.substring(1);

        lastAccessString = currentString;
    }

    String getTypedText()
    {
        return typedText;
    }

    void setTypedText(String text)
    {
        typedText = text;
    }

    @Override
    public Editable getEditable()
    {
        return editable;
    }

}