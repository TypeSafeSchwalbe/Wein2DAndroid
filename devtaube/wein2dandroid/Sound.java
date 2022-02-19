package devtaube.wein2dandroid;

public class Sound {

    private int soundID = -1;
    private int streamID;
    protected static GameView gameView;

    public Sound(int resource)
    {
        soundID = gameView.soundPool.load(gameView.wein2DApplication, resource, 0);
    }

    public void play()
    {
        streamID = gameView.soundPool.play(soundID, 1, 1, 0, 0, 1);
    }
    public void stop()
    {
        gameView.soundPool.stop(streamID);
    }
}
