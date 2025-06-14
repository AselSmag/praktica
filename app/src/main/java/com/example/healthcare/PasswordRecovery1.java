package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class PasswordRecovery1 extends AppCompatActivity {
    private TextInputEditText emailEditText;

    private Button loginButton;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery1);

        // Инициализация элементов UI
        initViews();

        // Настройка обработчиков событий
        setupListeners();
    }
    private void initViews() {
        emailEditText = findViewById(R.id.emailEditText);
        loginButton = findViewById(R.id.loginButton);
        backButton = findViewById(R.id.backButton);
    }
    private void setupListeners() {
        // Обработчик кнопки "Назад"
        backButton.setOnClickListener(v -> onBackPressed());
        // Обработчик кнопки "Продолжить"
        loginButton.setOnClickListener(v -> attemptLogin());
    }
    private void attemptLogin() {
        String email = emailEditText.getText().toString().trim();

        if (!validateEmail(email)) {
            emailEditText.setError("Введите корректный email (формат: name@domain.ru)");
            return;
        }
        // Если валидация пройдена, переходим на следующий экран
        Intent intent = new Intent(PasswordRecovery1.this, PasswordRecovery2.class);
        startActivity(intent);
    }
    private boolean validateEmail(String email) {
        // Паттерн для email: name@domenname.ru (только маленькие буквы и цифры)
        String emailPattern = "^[a-z0-9]+@[a-z0-9]+\\.[a-z]{2,3}$";
        return Pattern.compile(emailPattern).matcher(email).matches();
    }
}
