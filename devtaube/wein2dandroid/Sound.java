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

        Wein2DApplication.WEIN2DAPPLICATION.registerSound(this);
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
