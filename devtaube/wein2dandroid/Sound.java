package devtaube.wein2dandroid;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

public final class Sound
{

    private MediaPlayer mediaPlayer;
    private double lastVolume = 0.0;


    public Sound(int resource)
    {
        try
        {
            AssetFileDescriptor afd = Wein2DApplication.WEIN2DAPPLICATION.getResources().openRawResourceFd(resource);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.prepare();
            afd.close();
        }
        catch(Exception e) { e.printStackTrace(); }
    }

    public void loop(boolean looping)
    {
        mediaPlayer.setLooping(looping);
    }

    public void setVolume(double volume)
    {
        if(lastVolume == volume)
            return;

        float calcVolume = (float) (1 - (Math.log(1000 - (int) (volume * 1000)) / Math.log(1000)));
        mediaPlayer.setVolume(calcVolume, calcVolume);
        lastVolume = volume;
    }

    public void play()
    {
        if(mediaPlayer.isPlaying())
        {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
        }
        mediaPlayer.start();
    }

    public void stop()
    {
        mediaPlayer.pause();
    }

    public boolean isPlaying()
    {
        return mediaPlayer.isPlaying();
    }

}
