package com.mobile.physiolink.async;

import android.content.Intent;
import android.widget.Toast;

import com.mobile.physiolink.LoginActivity;
import com.mobile.physiolink.model.user.User;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.service.validator.UserAuth;
import com.mobile.physiolink.ui.DoctorActivity;
import com.mobile.physiolink.ui.PSFActivity;
import com.mobile.physiolink.ui.PatientActivity;
import com.mobile.physiolink.util.FileManager;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginCallback implements Callback
{
    private final LoginActivity loginActivity;
    private final String username;

    public LoginCallback(LoginActivity loginActivity, String username)
    {
        super();
        this.loginActivity = loginActivity;
        this.username = username;
    }

    @Override
    public void onFailure(Call call, IOException e)
    {
        call.cancel();
        loginActivity.runOnUiThread(() ->
		{
			Toast.makeText(loginActivity,
                "Προέκυψε ένα σφάλμα δικτύου. Προσπαθήστε ξανά αργότερα!", Toast.LENGTH_SHORT).show();
		});
    }

    @Override
    public void onResponse(Call call, Response response)
    {
        loginActivity.runOnUiThread(() ->
        {
            User user;
            try
            {
                user = UserAuth.prepareAndGetUser(response, username);
                if (user.isValid())
                {
                    Intent intent;
                    if (user.isPSF())
                        intent = new Intent(loginActivity, PSFActivity.class);
                    else if (user.isDoctor())
                        intent = new Intent(loginActivity, DoctorActivity.class);
                    else
                        intent = new Intent(loginActivity, PatientActivity.class);

                    Toast.makeText(loginActivity, "Επιτυχής Σύνδεση!", Toast.LENGTH_SHORT).show();

                    /* Save user obj */
                    FileManager.writeUserObj(user, "user.ser");

                    /* Pass user to UserHolder static class */
                    UserHolder.setInstance(user);
                    loginActivity.startActivity(intent);
                    loginActivity.finish();
                }
                else
                {
                    Toast.makeText(loginActivity, "Λάθος στοιχεία χρήστη", Toast.LENGTH_SHORT).show();
                }
            }
            catch (JSONException | IOException e)
            {
                e.printStackTrace();
                Toast.makeText(loginActivity,
                        "Προέκυψε ένα σφάλμα δικτύου. Προσπαθήστε ξανά αργότερα!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
