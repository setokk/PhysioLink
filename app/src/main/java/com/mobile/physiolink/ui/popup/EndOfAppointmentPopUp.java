package com.mobile.physiolink.ui.popup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.ItemDoctorAppointmentBinding;

public class EndOfAppointmentPopUp extends AppCompatDialogFragment {
    private final String title;
    private ImageView patientProfilePic;
    private TextView appointmentHour;
    private TextView appointmentName;
    private TextView selectServiceTitle;
    private TextView servicePriceTitle;
    private Spinner selectService;
    private TextView servicePrice;

    private DialogInterface.OnClickListener positiveListener;
    private DialogInterface.OnClickListener negativeListener;

    public EndOfAppointmentPopUp(ImageView patientProfilePic, TextView appointmentHour, TextView appointmentName){
        title = "Καταχώρηση ραντεβού";

        this.patientProfilePic = patientProfilePic;
        this.appointmentHour = appointmentHour;
        this.appointmentName = appointmentName;

        selectServiceTitle.setText("Επιλέξτε την παροχή:");
        servicePriceTitle.setText("Τιμή:");

    }

    public void setPositiveOnClick(DialogInterface.OnClickListener listener)
    {
        this.positiveListener = listener;
    }

    public void setNegativeOnClick(DialogInterface.OnClickListener listener)
    {
        this.negativeListener = listener;
    }

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_doctor_end_of_appointment,null);

        builder.setTitle(title)
                .setView(view)
                .setPositiveButton("Καταχώρηση", positiveListener)
                .setNegativeButton("Ακύρωση", negativeListener);

        return builder.create();
    }
}
