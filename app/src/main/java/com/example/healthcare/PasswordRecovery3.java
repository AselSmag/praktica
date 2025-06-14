package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class PasswordRecovery3 extends AppCompatActivity {

    private TextInputEditText passwordEditText, repeatPasswordEditText;
    private TextInputLayout passwordInputLayout, repeatPasswordInputLayout;
    private Button loginButton;
    private ImageButton backButton;
    private boolean isPasswordVisible = false;
    private boolean isRepeatPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery3);

        // Инициализация элементов
        initViews();

        // Настройка слушателей
        setupListeners();

        // Настройка переключения видимости пароля
        setupPasswordVisibilityToggle();
    }

    private void initViews() {
        passwordEditText = findViewById(R.id.passwordEditText);
        repeatPasswordEditText = findViewById(R.id.repeatPasswordEditText);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        repeatPasswordInputLayout = findViewById(R.id.repeatPasswordInputLayout);
        loginButton = findViewById(R.id.loginButton);
        backButton = findViewById(R.id.backButton);
    }

    private void setupListeners() {
        // Обработчик кнопки "Войти"
        loginButton.setOnClickListener(v -> {
            String password = passwordEditText.getText().toString().trim();
            String repeatPassword = repeatPasswordEditText.getText().toString().trim();

            if (validatePassword(password, repeatPassword)) {
                // Переход на экран входа
                Intent intent = new Intent(PasswordRecovery3.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Обработчик кнопки "Назад"
        backButton.setOnClickListener(v -> finish());

        // Слушатели для очистки ошибок при изменении текста
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                passwordInputLayout.setError(null);
                validatePasswordMatch();
            }
        });

        repeatPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                repeatPasswordInputLayout.setError(null);
                validatePasswordMatch();
            }
        });
    }

    private void setupPasswordVisibilityToggle() {
        // Переключение видимости для основного пароля
        passwordInputLayout.setEndIconOnClickListener(v -> {
            if (isPasswordVisible) {
                passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                passwordInputLayout.setEndIconDrawable(R.drawable.eyes1);
            } else {
                passwordEditText.setTransformationMethod(null);
                passwordInputLayout.setEndIconDrawable(R.drawable.eyes2);
            }
            isPasswordVisible = !isPasswordVisible;
            passwordEditText.setSelection(passwordEditText.getText().length());
        });

        // Переключение видимости для повторного пароля
        repeatPasswordInputLayout.setEndIconOnClickListener(v -> {
            if (isRepeatPasswordVisible) {
                repeatPasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                repeatPasswordInputLayout.setEndIconDrawable(R.drawable.eyes1);
            } else {
                repeatPasswordEditText.setTransformationMethod(null);
                repeatPasswordInputLayout.setEndIconDrawable(R.drawable.eyes2);
            }
            isRepeatPasswordVisible = !isRepeatPasswordVisible;
            repeatPasswordEditText.setSelection(repeatPasswordEditText.getText().length());
        });
    }

    private boolean validatePassword(String password, String repeatPassword) {
        boolean isValid = true;

        // Проверка основного пароля
        if (password.isEmpty()) {
            passwordInputLayout.setError("Введите пароль");
            isValid = false;
        } else if (password.length() < 6 || password.length() > 8) {
            passwordInputLayout.setError("Пароль должен быть от 6 до 8 символов");
            isValid = false;
        } else if (!password.matches("^(?=.*[a-zа-я])(?=.*[A-ZА-Я])(?=.*\\d)(?=.*[@#$%^&+=]).*$")) {
            passwordInputLayout.setError("Пароль должен содержать:\n- Прописную и заглавную букву\n- Цифру\n- Спецсимвол (@#$%^&+=)");
            isValid = false;
        }

        // Проверка повторного пароля
        if (repeatPassword.isEmpty()) {
            repeatPasswordInputLayout.setError("Повторите пароль");
            isValid = false;
        } else if (!password.equals(repeatPassword)) {
            repeatPasswordInputLayout.setError("Пароли не совпадают");
            isValid = false;
        }

        return isValid;
    }

    private void validatePasswordMatch() {
        String password = passwordEditText.getText().toString().trim();
        String repeatPassword = repeatPasswordEditText.getText().toString().trim();

        if (!password.isEmpty() && !repeatPassword.isEmpty() && !password.equals(repeatPassword)) {
            repeatPasswordInputLayout.setError("Пароли не совпадают");
        } else {
            repeatPasswordInputLayout.setError(null);
        }
    }
}