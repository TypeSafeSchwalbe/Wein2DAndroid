package devtaube.wein2dandroidexample;

import devtaube.wein2dandroid.App;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // call superclass and set flags
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // set content view to instance of wein2dandroid.App and pass its constructor this as well as a new instance of the TestApp class (needs to implement Application)
        this.setContentView(new App(this, new TestApp()));
    }
}
