package com.mobile.physiolink;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.concurrent.CountDownLatch;


public class ConfirmationServicePopUp extends AppCompatDialogFragment {


    //    Μεταβλητές Παροχής
    private String service_name;
    private String service_cost;
    private String service_id;
    private String service_description;

    private boolean update;
    private boolean ready;


    //    Constructor για Παροχές
    public ConfirmationServicePopUp(String name, String cost, String id, String description, boolean flag) {
        service_name = name;
        service_cost = cost;
        service_id = id;
        service_description = description;
        update = flag;
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
                        if (!update) {
                            Toast.makeText(getActivity(), "Εγινε αποθήκευση Παροχής!", Toast.LENGTH_SHORT).show();
                        } else {
                            ready = true;
                            Toast.makeText(getActivity(), "Εγινε αποθήκευση των αλλαγών!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Οχι", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ready = false;
                        Toast.makeText(getActivity(), "Δεν έγινε αποθήκευση!", Toast.LENGTH_SHORT).show();
                    }
                });

        return builder.create();
    }
}