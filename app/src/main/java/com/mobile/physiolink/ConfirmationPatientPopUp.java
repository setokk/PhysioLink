package com.mobile.physiolink;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ConfirmationPatientPopUp extends AppCompatDialogFragment {

    private String patientName;
    private String patientSurName;
    private String patientAddress;
    private String patientAmka;
    private String patientPhoneNumber;
    private String patientEmail;
    private String patientUserName;
    private String patientPassword;

    public ConfirmationPatientPopUp(String patientName, String patientSurName, String patientAddress, String patientAmka, String patientPhoneNumber, String patientEmail, String patientUserName, String patientPassword){
        this.patientName = patientName;
        this.patientSurName = patientSurName;
        this.patientAddress = patientAddress;
        this.patientAmka = patientAmka;
        this.patientPhoneNumber = patientPhoneNumber;
        this.patientEmail = patientEmail;
        this.patientUserName = patientUserName;
        this.patientPassword = patientPassword;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Αποθήκευση")
                .setMessage("Είστε σίγουρος για την επιλογή σας;")
                .setPositiveButton("Ναι", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getActivity(), "Εγινε αποθήκευση Ασθενή!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Οχι", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getActivity(), "Δεν έγινε αποθήκευση!", Toast.LENGTH_SHORT).show();
                    }
                });
        return builder.create();
    }
}
