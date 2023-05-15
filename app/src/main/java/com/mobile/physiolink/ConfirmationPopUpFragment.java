package com.mobile.physiolink;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class ConfirmationPopUpFragment extends AppCompatDialogFragment {

//    True Για Παροχες False για Γιατρους και Κλινικές
    private boolean flag;
//    Μεταβλητές Παροχής
    private String service_name;
    private String service_cost;
    private String service_id;
    private String service_description;

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

//    Constructor για Παροχές
    public ConfirmationPopUpFragment(String name, String cost, String id, String description){
        service_name = name;
        service_cost = cost;
        service_id = id;
        service_description = description;

        flag = true;
    }

//    Constructor για Γιατρό και Κλινική
    public ConfirmationPopUpFragment(String d_username, String d_password, String d_name, String d_sur_name, String d_afm, String d_phone, String c_name, String c_city, String c_address){
        doc_username = d_username;
        doc_password = d_password;
        doc_name = d_name;
        doc_sur_name = d_sur_name;
        doc_afm = d_afm;
        doc_phone = d_phone;

        clinic_name = c_name;
        clinic_city = c_city;
        clinic_address = c_address;

        flag = false;
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

                        if(flag){
                            Toast.makeText(getActivity(), "Εγινε αποθήκευση Παροχής!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity(), "Εγινε αποθήκευση Φυσιοθεραπευτηρίου!", Toast.LENGTH_SHORT).show();
                        }

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