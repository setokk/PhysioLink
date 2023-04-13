package com.mobile.physiolink;

import static com.mobile.physiolink.model.validator.LoginInputValidator.validateUser;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mobile.physiolink.model.user.User;
import com.mobile.physiolink.databinding.ActivityLoginBinding;
import com.mobile.physiolink.ui.DoctorActivity;
import com.mobile.physiolink.ui.PSFActivity;
import com.mobile.physiolink.ui.PatientActivity;

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

        binding.btnLogin.setOnClickListener((view) -> validateCredentialsAndNavigate());
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
     * @see com.mobile.physiolink.model.validator.LoginInputValidator
     */
    private void validateCredentialsAndNavigate()
    {
        /* Get and sanitize credentials */
        String username = Objects.requireNonNull(binding.editTextUsername.getText())
                .toString()
                .replaceAll(" ", ""); // Remove whitespaces

        String password = Objects.requireNonNull(binding.editTextPassword.getText())
                .toString()
                .replaceAll(" ", ""); // Remove whitespaces;

        if (username.isEmpty() || password.isEmpty())
            return;

        /* Validate credentials */
        User user = validateUser(username, password);
        if (user.isValid())
        {
            Intent intent;
            if (user.isPSF())
                intent = new Intent(this, PSFActivity.class);
            else if (user.isDoctor())
                intent = new Intent(this, DoctorActivity.class);
            else
                intent = new Intent(this, PatientActivity.class);

            /* Pass user to home activity */
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        }
    }
}
