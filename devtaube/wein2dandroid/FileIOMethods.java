package devtaube.wein2dandroid;

import android.app.Activity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileIOMethods
{

    public static Wein2DApplication wein2DApplication;

    public static void serializeObject(Object object, String filename) {

        try {

            FileOutputStream fileOutputStream = wein2DApplication.openFileOutput(filename, Activity.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            System.out.println(objectOutputStream.toString());
            objectOutputStream.writeObject(object);
            fileOutputStream.getFD().sync();
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object deserializeObject(String filename) {

        try {

            FileInputStream fileIn = wein2DApplication.getApplicationContext().openFileInput(filename);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            return objectIn.readObject();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
