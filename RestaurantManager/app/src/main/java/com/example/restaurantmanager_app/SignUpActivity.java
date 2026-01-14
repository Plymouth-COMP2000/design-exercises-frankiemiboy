package com.example.restaurantmanager_app;

import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantmanager_app.user_management.AuthCallback;
import com.example.restaurantmanager_app.user_management.NewUser;
import com.example.restaurantmanager_app.user_management.User;

public class SignUpActivity extends AppCompatActivity {

    EditText firstNameInput;
    EditText lastNameInput;
    EditText emailInput;
    EditText contactInput;
    EditText passwordInput;
    EditText passwordInput2;
    TextView registerLink;
    Button submitButton;

    private NewUser newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_signup_page);

        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        emailInput = findViewById(R.id.emailInput);
        contactInput = findViewById(R.id.contactInput);
        passwordInput = findViewById(R.id.passwordInput);
        passwordInput2 = findViewById(R.id.passwordInput2);
        registerLink = findViewById(R.id.link_to_login);
        submitButton = findViewById(R.id.submitButton);

        newUser = new NewUser(this);

        submitButton.setOnClickListener(v -> {
            attemptRegister();
        });

        registerLink.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

    }

    private void attemptRegister() {
        String firstName = firstNameInput.getText().toString().trim();
        String lastName = lastNameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String contact = contactInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String password2 = passwordInput2.getText().toString().trim();
        String userType = "guest";
        String username = generateRandomUsername(firstName, lastName);

        // Check that all the fields have been filled in
        if (firstName.isEmpty() ||
            lastName.isEmpty() ||
            email.isEmpty() ||
            contact.isEmpty() ||
            password.isEmpty() ||
            password2.isEmpty()
        ) {
            // Handle empty fields
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that the passwords match
        if (!password.equals(password2)) {
            // Handle password mismatch
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }


        newUser.register(
                "10933458",
                new User(username, password, firstName, lastName, email, contact, userType),
                new AuthCallback() {
                    @Override
                    public void onSuccess(String userType) {
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(String message) {
                        // Handle registration failure
                    }
                }
        );
    }

    public String generateRandomUsername(String firstName, String lastName) {
        if (firstName == null) {
            firstName = "";
        }
        if (lastName == null) {
            lastName = "";
        }

        // 1. Sanitise inputs
        String cleanFirstName = firstName.trim().toLowerCase().replace("[^a-z0-9]", "");
        String cleanLastName = lastName.trim().toLowerCase().replace("[^a-z0-9]", "");

        // 2. Generate a random 4-digit number
        Random random = new Random();
        int randomSuffix = 1000 + random.nextInt(9000);

        // 3. Combine to form username
        return cleanFirstName + "_" + cleanLastName + "_" + randomSuffix;
    }
}
