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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.healthcare.Adapters.AppointmentAdapter;
import com.example.healthcare.Models.Appointment;
import com.example.healthcare.Models.Profile;
import com.example.healthcare.PinActivity;
import com.example.healthcare.R;
import com.example.healthcare.SignUpActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Pattern;

public class HomeFragment extends Fragment {
    TextView datetext;
    TextView timetext;
    TextView doctortext;
    TextView doctornametext;
    RecyclerView rvTickets;
    RecyclerView rvHistory;
    ImageView imgProfile;
    TextView profileName;

    private void init(View view){
        datetext = view.findViewById(R.id.date);
        timetext = view.findViewById(R.id.time);
        doctortext = view.findViewById(R.id.tvDoctorTitle);
        doctornametext = view.findViewById(R.id.tvDoctorName);
        rvTickets = view.findViewById(R.id.rvTickets);
        rvHistory = view.findViewById(R.id.rvHistory);
        imgProfile = view.findViewById(R.id.profileImg);
        profileName = view.findViewById(R.id.profileName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);
        init(view);
        //TODO
        DataBinding.saveBearerToken("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imh0Z3p3bG53bHZlbnZocm9pdW1tIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDkxOTA2OTcsImV4cCI6MjA2NDc2NjY5N30.BCX8XfGqeUsANL9xgcm01UN4M24aH9gE6hzc3yuZ7ZM");
        DataBinding.saveUuidUser("0bb2ed51-ec2f-45ed-ae4e-b3475a53c7a6");
        //TODO
        getCurrentUser();
        getAllAppointment();
        return view;
    }

    private void getCurrentUser(){
        SupabaseClient supabaseClient = new SupabaseClient();
        supabaseClient.fetchCurrentUser(new SupabaseClient.SBC_Callback(){

            @Override
            public void onFailure(IOException e) {
                requireActivity().runOnUiThread(() -> {
                    Log.e("getCurrentUser:onFailure", e.getLocalizedMessage());
                });
            }

            @Override
            public void onResponse(String responseBody) {
                requireActivity().runOnUiThread(() -> {
                    Log.e("getCurrentUser:onResponse", responseBody);
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Profile>>(){}.getType();
                    List<Profile> profiles = gson.fromJson(responseBody, type);
                    if (profiles != null && !profiles.isEmpty()){
                        Profile profile = profiles.get(0);
                        String url = "https://htgzwlnwlvenvhroiumm.supabase.co/storage/v1/object/public/avatars/";
                        Glide.with(requireContext())
                                .load(url + profile.getAvatar_url())
                                .placeholder(R.drawable.profileicon)
                                .error(R.drawable.profileicon)
                                .into(imgProfile);
                        profileName.setText(profile.getUsername());

                    }
                });
            }
        });
    }

    private void getAllAppointment(){
        SupabaseClient supabaseClient = new SupabaseClient();
        supabaseClient.fetchAllUserAppointment(new SupabaseClient.SBC_Callback() {
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
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Appointment>>(){}.getType();
                    List<Appointment> appointmentList = gson.fromJson(responseBody, type);
                    AppointmentAdapter appointmentAdapter = new AppointmentAdapter(getContext().getApplicationContext(), appointmentList);
                    rvTickets.setAdapter(appointmentAdapter);
                    rvTickets.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                });
            }
        });
    }
}