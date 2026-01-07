package com.example.restaurantmanager_app;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantmanager_app.user_management.AuthCallback;
import com.example.restaurantmanager_app.user_management.AuthManager;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button submitButton;

    private AuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        usernameInput = findViewById(R.id.username_emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        submitButton = findViewById(R.id.submitButton);

        authManager = new AuthManager(this);

        submitButton.setOnClickListener(v -> {
            attemptLogin();
        });
    }

    private void attemptLogin() {
        String username = usernameInput.getText().toString().trim(); // Get rid of any extra spaces
        String password = passwordInput.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT);
            return;
        }

        authManager.login(
                "10933458",
                username,
                password,
                new AuthCallback() {
                    @Override
                    public void onSuccess(String userType) {
                        navigateToMainActivity(userType);
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

