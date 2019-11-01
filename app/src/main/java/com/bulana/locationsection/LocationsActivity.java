package com.bulana.locationsection;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class LocationsActivity extends AppCompatActivity {
    public static final int ADD_LOCATION_REQUEST = 1;
    public static final int EDIT_LOCATION_REQUEST = 2;

    private LocationViewModel locationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddLocation = findViewById(R.id.button_add_location);
        buttonAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationsActivity.this, CaptureUpdateActivity.class);
                startActivityForResult(intent, ADD_LOCATION_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final LocationAdapter adapter = new LocationAdapter();
        recyclerView.setAdapter(adapter);

        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        locationViewModel.getAllLocations().observe(this, new Observer<List<Location>>() {
            @Override
            public void onChanged(@Nullable List<Location> locations) {
                adapter.submitList(locations);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                locationViewModel.delete(adapter.getLocationAt(viewHolder.getAdapterPosition()));
                Toast.makeText(LocationsActivity.this, "Location deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new LocationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Location location) {
                Intent intent = new Intent(LocationsActivity.this, CaptureUpdateActivity.class);
                intent.putExtra(CaptureUpdateActivity.EXTRA_ID, location.getId());
                intent.putExtra(CaptureUpdateActivity.EXTRA_CITY, location.getCity());
                intent.putExtra(CaptureUpdateActivity.EXTRA_LONGITUDE, location.getLongitude());
                intent.putExtra(CaptureUpdateActivity.EXTRA_LATITUDE, location.getLongitude());
                intent.putExtra(CaptureUpdateActivity.EXTRA_POSITION, location.getPosition());
                startActivityForResult(intent, EDIT_LOCATION_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_LOCATION_REQUEST && resultCode == RESULT_OK) {
            String city = data.getStringExtra(CaptureUpdateActivity.EXTRA_CITY);
            String longitude = data.getStringExtra(CaptureUpdateActivity.EXTRA_LONGITUDE);
            String latitude = data.getStringExtra(CaptureUpdateActivity.EXTRA_LATITUDE);
            int priority = data.getIntExtra(CaptureUpdateActivity.EXTRA_POSITION, 1);

            Location location = new Location(city, longitude, latitude, priority);
            locationViewModel.insert(location);

            Toast.makeText(this, "Location saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_LOCATION_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(CaptureUpdateActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Location can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String city = data.getStringExtra(CaptureUpdateActivity.EXTRA_CITY);
            String longitude = data.getStringExtra(CaptureUpdateActivity.EXTRA_LONGITUDE);
            String latitude = data.getStringExtra(CaptureUpdateActivity.EXTRA_LATITUDE);
            int position = data.getIntExtra(CaptureUpdateActivity.EXTRA_POSITION, 1);

            Location location = new Location(city, longitude, latitude, position);
            location.setId(id);
            locationViewModel.update(location);

            Toast.makeText(this, "Location updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Location not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_locations:
                locationViewModel.deleteAllLocations();
                Toast.makeText(this, "All locations deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
