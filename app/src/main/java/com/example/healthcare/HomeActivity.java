package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private int currentNavItem = R.id.navigation_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        setupNavigation();
    }
    private void setupNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            // Если уже на этой Activity, ничего не делаем
            if (currentNavItem == itemId) {
                return false;
            }

            // Определяем целевую Activity
            Class<?> targetActivity = getTargetActivity(itemId);

            if (targetActivity != null) {
                // Запускаем новую Activity
                startActivity(new Intent(HomeActivity.this, targetActivity));

                // Для красивого перехода
                overridePendingTransition(0, 0);

                // Обновляем текущий элемент
                currentNavItem = itemId;

                // Закрываем текущую Activity, если это не главная
                if (itemId != R.id.navigation_home) {
                    finish();
                }

                return true;
            }

            return false;
        });

        // Устанавливаем текущий элемент при создании
        bottomNavigationView.setSelectedItemId(currentNavItem);
    }

    private Class<?> getTargetActivity(int itemId) {
        if (itemId == R.id.navigation_home) {
            return HomeActivity.class;
        } else if (itemId == R.id.navigation_chat) {
            return ChatActivity.class;
        } else if (itemId == R.id.navigation_search) {
            return SearchActivity.class;
        } else if (itemId == R.id.navigation_history) {
            return HistoryActivity.class;
        } else if (itemId == R.id.navigation_profile) {
            return ProfileActivity.class;
        }
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Обновляем выделение при возврате на Activity
        bottomNavigationView.setSelectedItemId(currentNavItem);
    }
}