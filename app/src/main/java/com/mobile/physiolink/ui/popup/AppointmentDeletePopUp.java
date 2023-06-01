package com.mobile.physiolink.ui.popup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AppointmentDeletePopUp extends AppCompatDialogFragment{
    private final String title;
    private final String message;

    private final String positiveText;
    private final String negativeText;

    private DialogInterface.OnClickListener positiveListener;
    private DialogInterface.OnClickListener negativeListener;

    public AppointmentDeletePopUp(String title, String message,
                                  String positiveText, String negativeText) {
        this.title = title;
        this.message = message;
        this.positiveText = positiveText;
        this.negativeText = negativeText;
    }

    public void setPositiveOnClick(DialogInterface.OnClickListener listener) {
        this.positiveListener = listener;
    }

    public void setNegativeOnClick(DialogInterface.OnClickListener listener) {
        this.negativeListener = listener;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveText, positiveListener)
                .setNegativeButton(negativeText, negativeListener);

        return builder.create();
    }
}
