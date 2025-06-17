package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PinActivity extends AppCompatActivity {

    private EditText pin1, pin2, pin3, pin4;
    private TextView errorText;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnExit;
    private ImageView btnDel;
    private StringBuilder enteredPin = new StringBuilder();
    private static final String CORRECT_PIN = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        // Инициализация полей ввода PIN
        pin1 = findViewById(R.id.pin1);
        pin2 = findViewById(R.id.pin2);
        pin3 = findViewById(R.id.pin3);
        pin4 = findViewById(R.id.pin4);
        errorText = findViewById(R.id.errorText);

        // Инициализация кнопок цифр
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btn0 = findViewById(R.id.btn0);

        // Инициализация кнопок управления
        btnDel = findViewById(R.id.btndel);
        btnExit = findViewById(R.id.btnExit);

        // Установка обработчиков нажатий для цифровых кнопок
        View.OnClickListener numberClickListener = v -> {
            if (enteredPin.length() < 4) {
                Button button = (Button) v;
                enteredPin.append(button.getText().toString());
                updatePinDisplay();

                // Автопроверка при вводе 4 цифр
                if (enteredPin.length() == 4) {
                    checkPin();
                }
            }
        };

        // Назначение обработчиков для цифровых кнопок
        btn1.setOnClickListener(numberClickListener);
        btn2.setOnClickListener(numberClickListener);
        btn3.setOnClickListener(numberClickListener);
        btn4.setOnClickListener(numberClickListener);
        btn5.setOnClickListener(numberClickListener);
        btn6.setOnClickListener(numberClickListener);
        btn7.setOnClickListener(numberClickListener);
        btn8.setOnClickListener(numberClickListener);
        btn9.setOnClickListener(numberClickListener);
        btn0.setOnClickListener(numberClickListener);

        // Обработчик кнопки удаления
        btnDel.setOnClickListener(v -> {
            if (enteredPin.length() > 0) {
                enteredPin.deleteCharAt(enteredPin.length() - 1);
                updatePinDisplay();
                errorText.setVisibility(View.GONE);
            }
        });

        // Обработчик кнопки выхода
        btnExit.setOnClickListener(v -> {
            finish(); // Закрытие приложения
        });
    }

    private void updatePinDisplay() {
        // Очищаем все поля
        pin1.setText("");
        pin2.setText("");
        pin3.setText("");
        pin4.setText("");

        // Устанавливаем точки в заполненные поля
        switch (enteredPin.length()) {
            case 4:
                pin4.setText("•");
            case 3:
                pin3.setText("•");
            case 2:
                pin2.setText("•");
            case 1:
                pin1.setText("•");
        }
    }

    private void checkPin() {
        if (enteredPin.toString().equals(CORRECT_PIN)) {
            // Пин-код верный, переход на главный экран
            Intent intent = new Intent(PinActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Пин-код неверный, показываем ошибку
            errorText.setText("Неверный ПИН-код. Попробуйте снова.");
            errorText.setVisibility(View.VISIBLE);
            enteredPin.setLength(0);
            updatePinDisplay();
        }
    }

    @Override
    public void onBackPressed() {
        // Блокируем кнопку "Назад"
        super.onBackPressed();
        Toast.makeText(this, "Для выхода нажмите кнопку 'Выход'", Toast.LENGTH_SHORT).show();
    }
}