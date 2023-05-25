package com.mobile.physiolink;

import static com.mobile.physiolink.service.validator.UserAuth.sendAuthRequest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mobile.physiolink.async.LoginCallback;
import com.mobile.physiolink.databinding.ActivityLoginBinding;
import com.mobile.physiolink.service.validator.UserAuth;
import com.mobile.physiolink.ui.DoctorActivity;
import com.mobile.physiolink.ui.PSFActivity;

import org.json.JSONException;

import java.io.IOException;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity
{
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceBundle)
    {
        super.onCreate(savedInstanceBundle);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener((view) ->
        {
            try
            {
                validateCredentialsAndNavigate();
            }
            catch (IOException | InterruptedException | JSONException ignored)
            { Toast.makeText(this, "Προέκυψε ένα σφάλμα. Ελέγξτε την σύνδεση σας στο διαδίκτυο και δοκιμάστε ξανά!", Toast.LENGTH_SHORT).show(); }
        });
    }

    /**
     * @author Kote Kostandin (setokk) <br>
     * <a href="https://www.linkedin.com/in/kostandin-kote-255382223/">LinkedIn</a>
     * <br>
     * Sanitizes and validates the username and password fields and then navigates
     * to one of the three activities (PSF, Doctor, Patient) according to what type
     * of user logged in (if the attempt was successful).
     * <br><br>
     * If not successful, it displays a popup window with an error message.
     * @see UserAuth
     */
    private void validateCredentialsAndNavigate() throws IOException, InterruptedException, JSONException
    {
        /* Get and sanitize credentials */
        String username = Objects.requireNonNull(binding.editTextUsername.getText()).toString().trim();
        String password = Objects.requireNonNull(binding.editTextPassword.getText()).toString();

        if (username.isEmpty() || password.isEmpty())
        {
            startActivity(new Intent(this, DoctorActivity.class));
            Toast.makeText(this, "Δεν επιτρέπονται κενά πεδία!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (username.contains(" ") || password.contains(" "))
        {
            Toast.makeText(this, "Δεν επιτρέπονται κενά στα πεδία!", Toast.LENGTH_SHORT).show();
            return;
        }

        /* Validate credentials */
        sendAuthRequest(username, password, new LoginCallback(this, username));
    }
}
