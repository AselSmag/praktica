package com.example.healthcare.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcare.Models.Appointment;
import com.example.healthcare.R;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    private Context context;
    private List<Appointment> appointmentList;

    public AppointmentAdapter(Context context, List<Appointment> appointmentList) {
        this.context = context;
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public AppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.ViewHolder holder, int position) {
        Appointment appointment = appointmentList.get(position);
        holder.dateTv.setText(appointment.getDate());
        holder.timeTv.setText(appointment.getTime());
        holder.doctorTitleTv.setText(appointment.getDoctors().getDirections().getName());
        holder.doctorNameTv.setText(appointment.getDoctors().getFIO());
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTv, timeTv, doctorTitleTv, doctorNameTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTv = itemView.findViewById(R.id.date);
            timeTv = itemView.findViewById(R.id.time);
            doctorTitleTv = itemView.findViewById(R.id.tvDoctorTitle);
            doctorNameTv = itemView.findViewById(R.id.tvDoctorName);
        }
    }
}
