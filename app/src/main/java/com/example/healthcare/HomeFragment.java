package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcare.PinActivity;
import com.example.healthcare.R;
import com.example.healthcare.SignUpActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.regex.Pattern;

public class HomeFragment extends Fragment {
    TextView datetext;
    TextView timetext;
    TextView doctortext;
    TextView doctornametext;
    RecyclerView rvTickets;
    RecyclerView rvHistory;

    private void init(View view){
        datetext = view.findViewById(R.id.date);
        timetext = view.findViewById(R.id.time);
        doctortext = view.findViewById(R.id.tvDoctorTitle);
        doctornametext = view.findViewById(R.id.tvDoctorName);
        rvTickets = view.findViewById(R.id.rvTickets);
        rvHistory = view.findViewById(R.id.rvHistory);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);
        init(view);
        getAllAppointment();
        return view;
    }

    private void getAllAppointment(){
        SupabaseClient supabaseClient = new SupabaseClient();
        supabaseClient.fetchAllAppointment(new SupabaseClient.SBC_Callback() {
            @Override
            public void onFailure(IOException e) {
                requireActivity().runOnUiThread(() -> {
                    Log.e("getAllAppointment:onFailure", e.getLocalizedMessage());
                });
            }

            @Override
            public void onResponse(String responseBody) {
                requireActivity().runOnUiThread(() -> {
                    Log.e("getAllAppointment:onResponse", responseBody);
                });
            }
        });
    }
}