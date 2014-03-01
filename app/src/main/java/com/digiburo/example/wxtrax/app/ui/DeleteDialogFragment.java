package com.digiburo.example.wxtrax.app.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.digiburo.example.wxtrax.app.R;
import com.digiburo.example.wxtrax.lib.utility.LogFacade;

public class DeleteDialogFragment extends DialogFragment {

  /**
   * Fragment Factory
   * @param title
   * @return
   */
  public static DeleteDialogFragment newInstance(int title, int message) {
    DeleteDialogFragment fragment = new DeleteDialogFragment();
    Bundle bundle = new Bundle();
    bundle.putInt(MESSAGE_KEY, message);
    bundle.putInt(TITLE_KEY, title);
    fragment.setArguments(bundle);
    return(fragment);
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    int message = getArguments().getInt(MESSAGE_KEY);
    int title = getArguments().getInt(TITLE_KEY);

    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setIcon(android.R.drawable.ic_dialog_alert);
    builder.setMessage(message);
    builder.setCancelable(false);
    builder.setTitle(title);

    builder.setPositiveButton(R.string.alert_delete_yes, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int id) {
        LogFacade.debug(LOG_TAG, "yes noted:" + id);
        ((MainActivity) getActivity()).onStationDeleteYes();
      }
    });

    builder.setNegativeButton(R.string.alert_delete_no, new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
      LogFacade.debug(LOG_TAG, "no noted:" + id);
      ((MainActivity) getActivity()).onStationDeleteNo();
      }
    });

    Dialog result = builder.create();
    return(result);
  }

  //
  public static final String MESSAGE_KEY = "message_key";
  public static final String TITLE_KEY = "title_key";

  //
  public static final String LOG_TAG = DeleteDialogFragment.class.getName();
}
/*
 * Copyright 2013 Digital Burro, INC
 * Created on Jan 5, 2013 by gsc
 */
