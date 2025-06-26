package com.example.healthcare;
import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SupabaseClient {
    public interface SBC_Callback{
        public void onFailure(IOException e);
        public void onResponse(String responseBody);
    }
    public static  String DOMAIN_NAME = "https://htgzwlnwlvenvhroiumm.supabase.co";
    public static  String REST_PATH = "rest/v1/";
    public static  String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imh0Z3p3bG53bHZlbnZocm9pdW1tIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDkxOTA2OTcsImV4cCI6MjA2NDc2NjY5N30.BCX8XfGqeUsANL9xgcm01UN4M24aH9gE6hzc3yuZ7ZM";
    public static  String BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imh0Z3p3bG53bHZlbnZocm9pdW1tIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDkxOTA2OTcsImV4cCI6MjA2NDc2NjY5N30.BCX8XfGqeUsANL9xgcm01UN4M24aH9gE6hzc3yuZ7ZM";
    OkHttpClient client = new OkHttpClient();
    public void fetchAllAppointment(final SBC_Callback callback){
/*        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();*/

        Request request = new Request.Builder()
                //.url("https://htgzwlnwlvenvhroiumm.supabase.co/rest/v1/appointment?select=*,doctors(*)")
                .url(DOMAIN_NAME + REST_PATH + "appointment?select=*,doctors(*)")
                //.addHeader("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imh0Z3p3bG53bHZlbnZocm9pdW1tIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDkxOTA2OTcsImV4cCI6MjA2NDc2NjY5N30.BCX8XfGqeUsANL9xgcm01UN4M24aH9gE6hzc3yuZ7ZM")
                .addHeader("apikey", API_KEY)
                //.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imh0Z3p3bG53bHZlbnZocm9pdW1tIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDkxOTA2OTcsImV4cCI6MjA2NDc2NjY5N30.BCX8XfGqeUsANL9xgcm01UN4M24aH9gE6hzc3yuZ7ZM")
                .addHeader("Authorization", BEARER_TOKEN)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String responseBody = response.body().string();
                    callback.onResponse(responseBody);
                }else{
                    callback.onFailure(new IOException("Ошибка сервера: "+ response));
                }
            }
        });
    }
}