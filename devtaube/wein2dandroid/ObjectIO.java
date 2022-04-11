package devtaube.wein2dandroid;

import android.app.Activity;

import java.io.*;

public final class ObjectIO
{

    private ObjectIO() {}


    public static void serializeObject(Object object, String filename)
    {

        try
        {
            FileOutputStream fileOutputStream = Wein2DApplication.WEIN2DAPPLICATION.openFileOutput(filename, Activity.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            fileOutputStream.getFD().sync();
            objectOutputStream.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static Object deserializeObject(String filename)
    {

        try
        {

            FileInputStream fileIn = Wein2DApplication.WEIN2DAPPLICATION.getApplicationContext().openFileInput(filename);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            return objectIn.readObject();
        }
        catch(Exception e) { e.printStackTrace(); }
        return null;
    }
}
