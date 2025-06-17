package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RepeatPin extends AppCompatActivity {
    private ImageButton backButton;
    private EditText pin1, pin2, pin3, pin4;
    private TextView errorText;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0;
    private ImageView btnDel;
    private StringBuilder enteredPin = new StringBuilder();
    private String correctPin; // PIN, который нужно повторить

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat_pin);

        // Получаем PIN из предыдущего экрана
        correctPin = getIntent().getStringExtra("CREATED_PIN");

        initViews();
        setupListeners();
    }

    private void initViews() {
        backButton = findViewById(R.id.backButton);
        pin1 = findViewById(R.id.pin1);
        pin2 = findViewById(R.id.pin2);
        pin3 = findViewById(R.id.pin3);
        pin4 = findViewById(R.id.pin4);
        errorText = findViewById(R.id.errorText);

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

        btnDel = findViewById(R.id.btndel);
    }

    private void setupListeners() {
        backButton.setOnClickListener(v -> onBackPressed());

        View.OnClickListener numberClickListener = v -> {
            if (enteredPin.length() < 4) {
                Button button = (Button) v;
                enteredPin.append(button.getText().toString());
                updatePinDisplay();

                if (enteredPin.length() == 4) {
                    checkPin();
                }
            }
        };

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

        btnDel.setOnClickListener(v -> {
            if (enteredPin.length() > 0) {
                enteredPin.deleteCharAt(enteredPin.length() - 1);
                updatePinDisplay();
                errorText.setVisibility(View.GONE);
            }
        });
    }

    private void updatePinDisplay() {
        pin1.setText("");
        pin2.setText("");
        pin3.setText("");
        pin4.setText("");

        switch (enteredPin.length()) {
            case 4: pin4.setText("•");
            case 3: pin3.setText("•");
            case 2: pin2.setText("•");
            case 1: pin1.setText("•");
        }
    }

    private void checkPin() {
        if (enteredPin.toString().equals(correctPin)) {
            // PIN совпадает, переходим на следующий экран
            Intent intent = new Intent(RepeatPin.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Пин-код неверный, показываем ошибку
            errorText.setText("ПИН-коды не совпадают. Попробуйте снова.");
            errorText.setVisibility(View.VISIBLE);
            enteredPin.setLength(0);
            updatePinDisplay();
        }
    }
}