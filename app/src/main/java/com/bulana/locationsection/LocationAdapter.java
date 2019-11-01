package com.bulana.locationsection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class LocationAdapter extends ListAdapter<Location, LocationAdapter.LocationHolder> {
    private OnItemClickListener listener;

    public LocationAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Location> DIFF_CALLBACK = new DiffUtil.ItemCallback<Location>() {
        @Override
        public boolean areItemsTheSame(Location oldItem, Location newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Location oldItem, Location newItem) {
            return oldItem.getCity().equals(newItem.getCity()) &&
                    oldItem.getLongitude().equals(newItem.getLongitude()) &&
                    oldItem.getLatitude().equals(newItem.getLatitude()) &&
                    oldItem.getPosition() == newItem.getPosition();
        }
    };

    @NonNull
    @Override
    public LocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_item, parent, false);
        return new LocationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationHolder holder, int position) {
        Location currentLocation = getItem(position);
        holder.textViewCity.setText(currentLocation.getCity());
        holder.textViewLongitude.setText(currentLocation.getLongitude());
        holder.textViewLatitude.setText(currentLocation.getLatitude());
    }

    public Location getLocationAt(int position) {
        return getItem(position);
    }

    class LocationHolder extends RecyclerView.ViewHolder {
        private TextView textViewCity;
        private TextView textViewLongitude;
        private TextView textViewLatitude;

        public LocationHolder(View itemView) {
            super(itemView);
            textViewCity = itemView.findViewById(R.id.text_view_city);
            textViewLongitude = itemView.findViewById(R.id.text_view_longitude);
            textViewLatitude = itemView.findViewById(R.id.text_view_latitude);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Location location);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
