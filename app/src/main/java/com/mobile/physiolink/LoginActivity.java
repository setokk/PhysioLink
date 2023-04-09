package com.mobile.physiolink;

import static com.mobile.physiolink.businesslogic.validator.LoginInputValidator.NOT_VALID;
import static com.mobile.physiolink.businesslogic.validator.LoginInputValidator.validateUser;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mobile.physiolink.businesslogic.user.User;
import com.mobile.physiolink.databinding.ActivityLoginBinding;
import com.mobile.physiolink.ui.HomeActivity;

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
        setTitle("PhysioLink");

        binding.btnLogin.setOnClickListener((view) ->
        {
            /* Validate credentials */
            String username = Objects.requireNonNull(binding.editTextUsername.getText()).toString();
            String password = Objects.requireNonNull(binding.editTextPassword.getText()).toString();

            User user = validateUser(username, password).orElse(new User(NOT_VALID));
            if (user.isValid())
            {
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("user", user); // Pass user to home activity
                startActivity(intent);
                finish();
            }
        });
    }
}
