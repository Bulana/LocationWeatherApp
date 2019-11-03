package com.bulana.locationsection;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class LocationAdapter extends ListAdapter<Location, LocationAdapter.LocationHolder> {
    private OnItemClickListener listener;
    private Context context;
    long DURATION = 150;
    private boolean on_attach = true;

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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item, parent, false);
        context = parent.getContext();
        return new LocationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final LocationHolder holder, int position) {
        Location currentLocation = getItem(position);
        holder.textViewCity.setText(currentLocation.getCity());
        holder.textViewLongitude.setText(currentLocation.getLongitude());
        holder.textViewLatitude.setText(currentLocation.getLatitude());

        FromRightToLeft(holder.itemView, position);
    }

    public Location getLocationAt(int position) {
        return getItem(position);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                on_attach = false;
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        super.onAttachedToRecyclerView(recyclerView);
    }

    private void FromRightToLeft(View itemView, int i) {
        if(!on_attach){
            i = -1;
        }
        boolean not_first_item = i == -1;
        i = i + 1;
        itemView.setTranslationX(itemView.getX() + 400);
        itemView.setAlpha(0.f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(itemView, "translationX", itemView.getX() + 400, 0);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(itemView, "alpha", 1.f);
        ObjectAnimator.ofFloat(itemView, "alpha", 0.f).start();
        animatorTranslateY.setStartDelay(not_first_item ? DURATION : (i * DURATION));
        animatorTranslateY.setDuration((not_first_item ? 2 : 1) * DURATION);
        animatorSet.playTogether(animatorTranslateY, animatorAlpha);
        animatorSet.start();
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
