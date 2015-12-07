package com.digiburo.wxtrax.audio;

import android.content.Context;

import com.digiburo.wxtraxlib.utility.UserPreferenceHelper;

/**
 * audio facade
 */
public class AudioFacade {
  public static final String LOG_TAG = AudioFacade.class.getName();

  //
  private static final AudioHelper audioHelper = new AudioHelper();
  private static final UserPreferenceHelper uph = new UserPreferenceHelper();

  /**
   * audio cue for page transitions
   * @param context
   */
  public void playTransitionCue(Context context) {
    if (uph.isAudioCue(context)) {
      audioHelper.playStreak(context);
    }
  }
}
