package com.example.healthcare;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.PinActivity;
import com.example.healthcare.R;
import com.example.healthcare.SignUpActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity {

    private TextInputEditText emailEditText, passwordEditText;
    private TextInputLayout passwordInputLayout;
    private Button loginButton;
    private TextView signUpTextView, forgotPasswordTextView;
    private ImageButton backButton;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Инициализация элементов UI
        initViews();

        // Настройка обработчиков событий
        setupListeners();
    }

    private void initViews() {
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        loginButton = findViewById(R.id.loginButton);
        signUpTextView = findViewById(R.id.signUpTextView);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);
        backButton = findViewById(R.id.backButton);
    }

    private void setupListeners() {
        // Обработчик кнопки "Назад"
        backButton.setOnClickListener(v -> onBackPressed());

        // Обработчик переключения видимости пароля
        passwordInputLayout.setEndIconOnClickListener(v -> togglePasswordVisibility());

        // Обработчик кнопки "Войти"
        loginButton.setOnClickListener(v -> attemptLogin());

        // Обработчик текста "Зарегистрируйтесь"
        signUpTextView.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        // Обработчик текста "Забыли пароль?"
        forgotPasswordTextView.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this, PasswordRecovery1.class);
            startActivity(intent);
        });
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Скрыть пароль
            passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            passwordInputLayout.setEndIconDrawable(R.drawable.eyes1);
        } else {
            // Показать пароль
            passwordEditText.setTransformationMethod(null);
            passwordInputLayout.setEndIconDrawable(R.drawable.eyes2);
        }
        isPasswordVisible = !isPasswordVisible;
        // Переместить курсор в конец текста
        passwordEditText.setSelection(passwordEditText.getText().length());
    }

    private void attemptLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (!validateEmail(email)) {
            emailEditText.setError("Введите корректный email (формат: name@domain.ru)");
            return;
        }

        if (!validatePassword(password)) {
            passwordEditText.setError("Пароль не может быть пустым");
            return;
        }

        // Если валидация пройдена, переходим на экран PIN
        Intent intent = new Intent(SignInActivity.this, PinActivity.class);
        startActivity(intent);
    }

    private boolean validateEmail(String email) {
        // Паттерн для email: name@domenname.ru (только маленькие буквы и цифры)
        String emailPattern = "^[a-z0-9]+@[a-z0-9]+\\.[a-z]{2,3}$";
        return Pattern.compile(emailPattern).matcher(email).matches();
    }

    private boolean validatePassword(String password) {
        return !password.isEmpty();
    }
}