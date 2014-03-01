package com.digiburo.example.wxtrax.lib.utility;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.digiburo.example.wxtrax.lib.R;

/**
 * facade for MediaPlayer
 *
 * @author gsc
 */
public class AudioHelper implements AudioManager.OnAudioFocusChangeListener {

  public void playStreak(int id, Context context) {
    AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
 
    int result = am.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
    
    switch(result) {
    case AudioManager.AUDIOFOCUS_REQUEST_FAILED:
      LogFacade.debug(LOG_TAG, "audiofocus request failure");
      break;
    case AudioManager.AUDIOFOCUS_REQUEST_GRANTED:
      LogFacade.debug(LOG_TAG, "audiofocus request granted");
      
      AudioPlayerListener apl = new AudioPlayerListener();

      MediaPlayer mp = MediaPlayer.create(context, id);
//      MediaPlayer mp = MediaPlayer.create(context, R.raw.streak);
      mp.setOnCompletionListener(apl);
      mp.setOnErrorListener(apl);
      mp.setOnInfoListener(apl);
      mp.setVolume(0.7f, 0.7f);
      mp.setLooping(false);    
      mp.start();

      break;
    default:
      LogFacade.debug(LOG_TAG, "unknown focus request response:" + result);
      break;
    }
    
    am.abandonAudioFocus(this);
  }

  private class AudioPlayerListener implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener {
    public void onCompletion(MediaPlayer mp) {
      LogFacade.entry(LOG_TAG, "onCompletion");
      mp.release();
    }
    
    public boolean onError(MediaPlayer mp, int what, int extra) {
      LogFacade.entry(LOG_TAG, "onError");
      
      switch(what) {
      case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
        LogFacade.debug(LOG_TAG, "media error not valid");
        break;
      case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
        LogFacade.debug(LOG_TAG, "media error server died");
        break;
      case MediaPlayer.MEDIA_ERROR_UNKNOWN:
        LogFacade.debug(LOG_TAG, "media error unknown");
        break;
      default:
        LogFacade.debug(LOG_TAG, "media error default");
      }
      
      return(false);
    }
    
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
      LogFacade.entry(LOG_TAG, "onInfo");
      
      switch(what) {
      case MediaPlayer.MEDIA_INFO_UNKNOWN:
        LogFacade.debug(LOG_TAG, "media info unknown");
        break;
      case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
        LogFacade.debug(LOG_TAG, "media info video track lag");
        break;
      case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
        LogFacade.debug(LOG_TAG, "media info bad interleave");
        break;
      case MediaPlayer.MEDIA_INFO_METADATA_UPDATE:
        LogFacade.debug(LOG_TAG, "media info metadata update");
        break;
      case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
        LogFacade.debug(LOG_TAG, "media info not seekable");
        break;
      default:
        LogFacade.debug(LOG_TAG, "media info default");
      }
      
      return(false);
    } 
  }

  // OnAudioFocusChangeListener
  public void onAudioFocusChange(int focusChange) {
    LogFacade.entry(LOG_TAG, "onAudioFocusChange:" + focusChange);
    
    switch(focusChange) {
    case AudioManager.AUDIOFOCUS_GAIN:
      LogFacade.debug(LOG_TAG, "audiofocus gain");
      break;
    case AudioManager.AUDIOFOCUS_LOSS:
      LogFacade.debug(LOG_TAG, "audiofocus loss");
      break;
    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
      LogFacade.debug(LOG_TAG, "audiofocus loss transient");
      break;
    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
      LogFacade.debug(LOG_TAG, "audiofocus loss transient can duck");
      break;
    default:
      LogFacade.debug(LOG_TAG, "unknown focus change");
      break;
    }
  }

  //
  public final String LOG_TAG = getClass().getName();
}

/*
 * Copyright 2010 Digital Burro, INC
 * Created on Sep 15, 2010 by gsc
 */