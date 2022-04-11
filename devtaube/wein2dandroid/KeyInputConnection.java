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