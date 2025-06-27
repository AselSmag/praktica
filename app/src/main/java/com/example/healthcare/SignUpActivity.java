package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText emailEditText, nameEditText, phoneEditText,
            birthDateEditText, passwordEditText, repeatPasswordEditText;
    private TextInputLayout emailInputLayout, nameInputLayout, phoneInputLayout,
            birthDateInputLayout, passwordInputLayout, repeatPasswordInputLayout;
    private RadioGroup genderRadioGroup;
    private CheckBox termsCheckBox;
    private Button nextButton;
    ImageButton backButton;
    private TextView signInTextView;
    private boolean isPasswordVisible = false;
    private boolean isPasswordVisible1 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Закрывает текущую Activity и возвращает на предыдущую
            }
        });

        initViews();
        setupListeners();
        setupTextWatchers();
        setupInputFilters();
    }

    private void initViews() {
        emailEditText = findViewById(R.id.emailEditText);
        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        birthDateEditText = findViewById(R.id.birthDateEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        repeatPasswordEditText = findViewById(R.id.repeatPasswordEditText);

        emailInputLayout = findViewById(R.id.emailInputLayout);
        nameInputLayout = findViewById(R.id.nameInputLayout);
        phoneInputLayout = findViewById(R.id.phoneInputLayout);
        birthDateInputLayout = findViewById(R.id.birthDateInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        repeatPasswordInputLayout = findViewById(R.id.repeatPasswordInputLayout);

        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        termsCheckBox = findViewById(R.id.termsCheckBox);
        nextButton = findViewById(R.id.nextButton);
        signInTextView = findViewById(R.id.signInTextView);
    }

    private void setupListeners() {
        nextButton.setOnClickListener(v -> attemptRegistration());
        signInTextView.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(intent);
        });

        // Обработчик переключения видимости пароля
        passwordInputLayout.setEndIconOnClickListener(v -> togglePasswordVisibility());
        repeatPasswordInputLayout.setEndIconOnClickListener(v -> togglerepeatPasswordVisibility());

        // Настройка ввода пароля
        passwordEditText.setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        repeatPasswordEditText.setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
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
        passwordEditText.setSelection(passwordEditText.getText().length());
    }

    private void togglerepeatPasswordVisibility() {
        if (isPasswordVisible1) {
            // Скрыть пароль
            repeatPasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            repeatPasswordInputLayout.setEndIconDrawable(R.drawable.eyes1);
        } else {
            // Показать пароль
            repeatPasswordEditText.setTransformationMethod(null);
            repeatPasswordInputLayout.setEndIconDrawable(R.drawable.eyes2);
        }
        isPasswordVisible1 = !isPasswordVisible1;
        repeatPasswordEditText.setSelection(repeatPasswordEditText.getText().length());
    }

    private void setupTextWatchers() {
        // Устанавливаем "+7" при получении фокуса
        phoneEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && phoneEditText.getText().toString().isEmpty()) {
                phoneEditText.setText("+7");
                phoneEditText.setSelection(2);
            }
        });

        // Маска для телефона (+7 и 10 цифр)
        phoneEditText.addTextChangedListener(new TextWatcher() {
            private boolean isFormatting;
            private boolean deletingHyphen;
            private int hyphenStart;
            private String beforeHyphen;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (isFormatting) return;

                // Запоминаем текст перед удалением для обработки удаления "+7"
                if (count > 0 && start <= 2 && s.toString().startsWith("+7")) {
                    deletingHyphen = true;
                    hyphenStart = start;
                    beforeHyphen = s.toString();
                } else {
                    deletingHyphen = false;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isFormatting) return;

                // Если пользователь пытается удалить "+7", запрещаем это
                if (deletingHyphen && beforeHyphen != null && beforeHyphen.startsWith("+7")
                        && s.toString().length() < 2) {
                    isFormatting = true;
                    phoneEditText.setText("+7");
                    phoneEditText.setSelection(2);
                    isFormatting = false;
                    return;
                }

                // Если текст не начинается с +7, добавляем +7
                if (!s.toString().startsWith("+7")) {
                    isFormatting = true;
                    String phone = s.toString().replaceAll("[^0-9]", "");
                    if (phone.length() > 10) phone = phone.substring(0, 10);
                    phoneEditText.setText("+7" + phone);
                    phoneEditText.setSelection(phoneEditText.getText().length());
                    isFormatting = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isFormatting) return;

                // Ограничиваем длину номера (12 символов: +7 + 10 цифр)
                if (s.length() > 12) {
                    isFormatting = true;
                    s.delete(12, s.length());
                    isFormatting = false;
                }
            }
        });

        // Маска для даты рождения (дд.мм.гггг)
        birthDateEditText.addTextChangedListener(new TextWatcher() {
            private boolean isFormatting = false;
            private boolean deletingDot = false;
            private int lastLength = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (isFormatting) return;

                // Определяем, удаляется ли точка
                if (count == 1 && start < s.length() && s.charAt(start) == '.') {
                    deletingDot = true;
                } else {
                    deletingDot = false;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isFormatting) return;

                String currentText = s.toString();

                // Автоматическая расстановка точек
                if (!deletingDot && currentText.length() > lastLength) {
                    if (currentText.length() == 2 && before == 0) {
                        isFormatting = true;
                        birthDateEditText.setText(currentText + ".");
                        birthDateEditText.setSelection(3);
                        isFormatting = false;
                    } else if (currentText.length() == 5 && before == 0) {
                        isFormatting = true;
                        birthDateEditText.setText(currentText + ".");
                        birthDateEditText.setSelection(6);
                        isFormatting = false;
                    }
                }
                lastLength = currentText.length();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isFormatting) return;

                String currentText = s.toString();

                // Удаляем все точки, чтобы перепроверить формат
                String digitsOnly = currentText.replaceAll("[^0-9]", "");

                // Если пользователь ввел цифры без точек, форматируем заново
                if (digitsOnly.length() >= 2 && !currentText.contains(".") && !deletingDot) {
                    isFormatting = true;
                    String formatted = digitsOnly.substring(0, 2);
                    if (digitsOnly.length() > 2) {
                        formatted += "." + digitsOnly.substring(2, Math.min(4, digitsOnly.length()));
                    }
                    if (digitsOnly.length() > 4) {
                        formatted += "." + digitsOnly.substring(4, Math.min(8, digitsOnly.length()));
                    }
                    birthDateEditText.setText(formatted);
                    birthDateEditText.setSelection(formatted.length());
                    isFormatting = false;
                    return;
                }

                // Ограничение длины (дд.мм.гггг - максимум 10 символов)
                if (currentText.length() > 10) {
                    isFormatting = true;
                    s.delete(10, s.length());
                    isFormatting = false;
                }

                // Валидация даты при полном вводе
                if (currentText.length() == 10) {
                    if (!validateBirthDate(currentText)) {
                        birthDateInputLayout.setError("Введите корректную дату в формате дд.мм.гггг");
                    } else {
                        birthDateInputLayout.setError(null);
                    }
                } else if (currentText.length() > 0) {
                    birthDateInputLayout.setError("Введите полную дату в формате дд.мм.гггг");
                } else {
                    birthDateInputLayout.setError(null);
                }
            }
        });

        // Очистка ошибок при изменении текста
        setupClearErrorOnTextChange(emailEditText, emailInputLayout);
        setupClearErrorOnTextChange(nameEditText, nameInputLayout);
        setupClearErrorOnTextChange(phoneEditText, phoneInputLayout);
        setupClearErrorOnTextChange(birthDateEditText, birthDateInputLayout);
        setupClearErrorOnTextChange(passwordEditText, passwordInputLayout);
        setupClearErrorOnTextChange(repeatPasswordEditText, repeatPasswordInputLayout);
    }

    private void setupClearErrorOnTextChange(TextInputEditText editText, TextInputLayout layout) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                layout.setError(null);
                layout.setErrorEnabled(false);
            }
        });
    }

    private void setupInputFilters() {
        // Фильтр для ФИО (только русские буквы и пробелы)
        InputFilter nameFilter = (source, start, end, dest, dstart, dend) -> {
            for (int i = start; i < end; i++) {
                char c = source.charAt(i);
                if (!(Character.isLetter(c) && (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CYRILLIC ||
                        Character.isWhitespace(c)))) {
                    return "";
                }
            }
            return null;
        };
        nameEditText.setFilters(new InputFilter[]{nameFilter, new InputFilter.LengthFilter(50)});

        // Фильтр для даты рождения (только цифры)
        InputFilter dateFilter = (source, start, end, dest, dstart, dend) -> {
            for (int i = start; i < end; i++) {
                if (!Character.isDigit(source.charAt(i))) {
                    return "";
                }
            }
            return null;
        };
        birthDateEditText.setFilters(new InputFilter[]{dateFilter, new InputFilter.LengthFilter(10)});
    }

    private void attemptRegistration() {
        String email = emailEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String birthDate = birthDateEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String repeatPassword = repeatPasswordEditText.getText().toString().trim();
        int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();
        boolean termsAccepted = termsCheckBox.isChecked();

        // Валидация всех полей
        boolean isValid = true;

        if (!validateEmail(email)) {
            emailInputLayout.setError("Введите корректный email (формат: name@domain.ru)");
            isValid = false;
        }

        if (!validateName(name)) {
            nameInputLayout.setError("Введите ФИО (2 или 3 слова, только русские буквы)");
            isValid = false;
        }

        if (!validatePhone(phone)) {
            phoneInputLayout.setError("Введите корректный номер телефона (+7XXXXXXXXXX)");
            isValid = false;
        }

        if (!validateBirthDate(birthDate)) {
            birthDateInputLayout.setError("Введите корректную дату рождения (дд.мм.гггг)");
            isValid = false;
        }

        if (selectedGenderId == -1) {
            Toast.makeText(this, "Выберите пол", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        if (!validatePassword(password)) {
            passwordInputLayout.setError("Пароль: 6-8 символов, буквы (A-Z, a-z), цифры, спецсимволы");
            isValid = false;
        }

        if (!password.equals(repeatPassword)) {
            repeatPasswordInputLayout.setError("Пароли не совпадают");
            isValid = false;
        }

        if (!termsAccepted) {
            Toast.makeText(this, "Примите условия обслуживания", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        if (isValid) {
            Intent intent = new Intent(SignUpActivity.this, ConfirmActivity.class);
            startActivity(intent);
        }
    }

    private boolean validateEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.compile(emailPattern).matcher(email).matches();
    }

    private boolean validateName(String name) {
        // Проверка на 2 или 3 слова (ФИ или ФИО)
        String namePattern = "^[а-яА-ЯёЁ]+\\s[а-яА-ЯёЁ]+(\\s[а-яА-ЯёЁ]+)?$";
        return Pattern.compile(namePattern).matcher(name).matches();
    }

    private boolean validatePhone(String phone) {
        return phone.matches("^\\+7\\d{10}$");
    }

    private boolean validateBirthDate(String date) {
        // Проверка формата
        if (!date.matches("^\\d{2}\\.\\d{2}\\.\\d{4}$")) {
            return false;
        }

        // Разбиваем дату на части
        String[] parts = date.split("\\.");
        if (parts.length != 3) return false;

        int day, month, year;
        try {
            day = Integer.parseInt(parts[0]);
            month = Integer.parseInt(parts[1]);
            year = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            return false;
        }

        // Проверка года (1920-текущий год)
        Calendar current = Calendar.getInstance();
        int currentYear = current.get(Calendar.YEAR);
        if (year < 1920 || year > currentYear) {
            return false;
        }

        // Проверка месяца
        if (month < 1 || month > 12) {
            return false;
        }

        // Проверка дня
        Calendar cal = Calendar.getInstance();
        cal.setLenient(false);
        cal.set(year, month - 1, 1); // month is 0-based in Calendar
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        if (day < 1 || day > maxDay) {
            return false;
        }

        // Проверка что дата не в будущем
        cal.set(year, month - 1, day);
        Date birthDate = cal.getTime();
        Date today = new Date();

        return !birthDate.after(today);
    }

    private boolean validatePassword(String password) {
        // 6-8 символов, минимум 1 заглавная, 1 строчная, 1 цифра, 1 спецсимвол
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-zа-я])(?=.*[A-ZА-Я])(?=.*[@#$%^&+=])(?=\\S+$).{6,8}$";
        return Pattern.compile(passwordPattern).matcher(password).matches();
    }
}