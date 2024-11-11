package com.example.previsaodotempo.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.previsaodotempo.R;
import com.example.previsaodotempo.domain.WeatherCard;

import java.util.List;


public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    private List<WeatherCard> data;

    public WeatherAdapter(List<WeatherCard> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherCard item = data.get(position);
        holder.cityTextView.setText(item.getCity());
        holder.descriptionTextView.setText(item.getDescription());
        holder.dateTextView.setText(item.getDate());
        holder.temperatureTextView.setText(String.format("%.1f°C", item.getTemperature()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateWeatherList(List<WeatherCard> newWeatherList) {
        data.clear();
        data.addAll(newWeatherList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityTextView;
        TextView descriptionTextView;
        TextView dateTextView;
        TextView temperatureTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            cityTextView = itemView.findViewById(R.id.cityTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            temperatureTextView = itemView.findViewById(R.id.temperatureTextView);
        }
    }
}
