package com.mobile.physiolink.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mobile.physiolink.LoginActivity;
import com.mobile.physiolink.model.user.User;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.util.FileManager;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        FileManager.setPath(this);
        if (!FileManager.exists("user.ser"))
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        try
        {
            User user = FileManager.readUserObj("user.ser");
            Intent intent;
            if (user.isPSF())
                intent = new Intent(this, PSFActivity.class);
            else if (user.isDoctor())
                intent = new Intent(this, DoctorActivity.class);
            else
                intent = new Intent(this, PatientActivity.class);

            Toast.makeText(this, "Καλωσήρθατε ξανά, " + user.getUsername() + "!",
                    Toast.LENGTH_SHORT).show();

            /* Pass user to UserHolder static class */
            UserHolder.setInstance(user);
            startActivity(intent);
            finish();
        }
        catch (IOException|ClassNotFoundException e)
        {
            Toast.makeText(this, "Προέκυψε σοβαρό σφάλμα. Προσπαθήστε ξανά αργότερα...",
                    Toast.LENGTH_LONG).show();
        }
    }
}
