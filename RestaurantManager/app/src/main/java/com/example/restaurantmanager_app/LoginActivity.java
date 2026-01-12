package com.example.restaurantmanager_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantmanager_app.user_management.AuthCallback;
import com.example.restaurantmanager_app.user_management.AuthManager;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput;
    private EditText usernameInput;
    private EditText passwordInput;
    private Button submitButton;

    private AuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_login_page);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        submitButton = findViewById(R.id.submitButton);

        authManager = new AuthManager(this);

        submitButton.setOnClickListener(v -> {
            attemptLogin();
        });
    }

    private void attemptLogin() {
        String email = emailInput.getText().toString().trim(); // Get rid of any extra spaces
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT);
            return;
        }

        authManager.login(
                "10933458",
                email,
                password,
                new AuthCallback() {
                    @Override
                    public void onSuccess(String userType) {
                        navigateToMainActivity(userType);
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void navigateToMainActivity(String userType) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

