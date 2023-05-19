package com.mobile.physiolink;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ConfirmationClinicPopUp extends AppCompatDialogFragment {


    //    Μεταβλητές Γιατρού
    private String doc_username;
    private String doc_password;
    private String doc_name;
    private String doc_sur_name;
    private String doc_afm;
    private String doc_phone;

    //    Μεταβλητές Κλινικής
    private String clinic_name;
    private String clinic_city;
    private String clinic_address;


    //    Constructor για Γιατρό και Κλινική
    public ConfirmationClinicPopUp(String d_username, String d_password, String d_name, String d_sur_name, String d_afm, String d_phone, String c_name, String c_city, String c_address){
        doc_username = d_username;
        doc_password = d_password;
        doc_name = d_name;
        doc_sur_name = d_sur_name;
        doc_afm = d_afm;
        doc_phone = d_phone;

        clinic_name = c_name;
        clinic_city = c_city;
        clinic_address = c_address;
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

                        Toast.makeText(getActivity(), "Εγινε αποθήκευση Φυσιοθεραπευτηρίου!", Toast.LENGTH_SHORT).show();
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
