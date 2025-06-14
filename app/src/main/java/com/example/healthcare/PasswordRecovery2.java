package com.example.healthcare;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.PasswordRecovery3;
import com.example.healthcare.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class PasswordRecovery2 extends AppCompatActivity {

    private TextInputEditText emailcodEditText;
    private TextInputLayout emailInputLayout;
    private Button loginButton;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery2);

        // Инициализация элементов интерфейса
        initViews();

        // Настройка слушателей
        setupListeners();

        // Настройка фильтра для ввода только цифр
        setupInputFilters();
    }

    private void initViews() {
        emailcodEditText = findViewById(R.id.emailcodEditText);
        emailInputLayout = findViewById(R.id.emailInputLayout);
        loginButton = findViewById(R.id.loginButton);
        backButton = findViewById(R.id.backButton);
    }

    private void setupListeners() {
        // Обработчик кнопки "Далее"
        loginButton.setOnClickListener(v -> {
            String code = emailcodEditText.getText().toString().trim();

            if (validateCode(code)) {
                // Переход на следующий экран
                Intent intent = new Intent(PasswordRecovery2.this, PasswordRecovery3.class);
                startActivity(intent);
            } else {
                // Показать ошибку
                emailInputLayout.setError("Введите 6-значный код");
            }
        });

        // Обработчик кнопки "Назад"
            backButton.setOnClickListener(v -> onBackPressed());

        // Слушатель для очистки ошибки при изменении текста
        emailcodEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                emailInputLayout.setError(null);
            }
        });
    }

    private void setupInputFilters() {
        // Фильтр для ввода только цифр
        InputFilter[] filters = new InputFilter[] {
                new InputFilter.LengthFilter(6), // Максимум 6 символов
                (source, start, end, dest, dstart, dend) -> {
                    for (int i = start; i < end; i++) {
                        if (!Character.isDigit(source.charAt(i))) {
                            return ""; // Блокировать ввод не-цифр
                        }
                    }
                    return null;
                }
        };
        emailcodEditText.setFilters(filters);
    }

    private boolean validateCode(String code) {
        // Проверка что код состоит ровно из 6 цифр
        return code.length() == 6 && code.matches("\\d+");
    }
}