package com.mobile.physiolink.ui.popup;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.mobile.physiolink.R;

public class AppointmentRejectPopUp extends AppCompatDialogFragment{

    private final String title;
    private TextView messageText;
    private EditText reason;
    private final String positiveText;
    private final String negativeText;

    private DialogInterface.OnClickListener positiveListener;
    private DialogInterface.OnClickListener negativeListener;

    public AppointmentRejectPopUp(String title,
                             String positiveText, String negativeText)
    {
        this.title = title;
        this.positiveText = positiveText;
        this.negativeText = negativeText;
    }

    public void setPositiveOnClick(DialogInterface.OnClickListener listener)
    {
        this.positiveListener = listener;
    }

    public void setNegativeOnClick(DialogInterface.OnClickListener listener)
    {
        this.negativeListener = listener;
    }

    @SuppressLint("MissingInflatedId")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_doctor_reject_pop_up,null);

        builder.setTitle(title)
                .setView(view)
                .setPositiveButton(positiveText, positiveListener)
                .setNegativeButton(negativeText, negativeListener);
        messageText = view.findViewById(R.id.reasonTextView);
        messageText.setText("Παραθέστε τον λόγο απόρριψης του αιτήματος:");
        reason = view.findViewById(R.id.editTextRejectionReason);


        return builder.create();
    }
    public String getReason(){
        return reason.getText().toString();
    }
}

