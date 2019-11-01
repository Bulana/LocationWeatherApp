package com.bulana.locationsection;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CaptureUpdateActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_CITY = "EXTRA_CITY";
    public static final String EXTRA_LONGITUDE = "EXTRA_LONGITUDE";
    public static final String EXTRA_LATITUDE = "EXTRA_LATITUDE";
    public static final String EXTRA_POSITION = "EXTRA_POSITION";

    private EditText editTextCity;
    private NumberPicker numberPickerPosition;
    private EditText editTextLongitude;
    private EditText editTextLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        editTextCity = findViewById(R.id.edit_text_title);
        editTextLongitude = findViewById(R.id.edit_text_longitude);
        editTextLatitude = findViewById(R.id.edit_text_latitude);
        numberPickerPosition = findViewById(R.id.number_picker_priority);

        numberPickerPosition.setMinValue(1);
        numberPickerPosition.setMaxValue(500);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Location");
            editTextCity.setText(intent.getStringExtra(EXTRA_CITY));
            editTextLongitude.setText(intent.getStringExtra(EXTRA_LONGITUDE));
            editTextLatitude.setText(intent.getStringExtra(EXTRA_LATITUDE));
            numberPickerPosition.setValue(intent.getIntExtra(EXTRA_POSITION, 1));
        } else {
            setTitle("Add Location");
        }
    }

    private void saveLocation() {
        String city = editTextCity.getText().toString();
        String longitude = editTextLongitude.getText().toString();
        String latitude = editTextLatitude.getText().toString();
        int priority = numberPickerPosition.getValue();

        if (city.trim().isEmpty() || longitude.trim().isEmpty() || latitude.trim().isEmpty() ) {
            Toast.makeText(this, "Please insert a city and lat long", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_CITY, city);
        data.putExtra(EXTRA_LONGITUDE, longitude);
        data.putExtra(EXTRA_LATITUDE, latitude);
        data.putExtra(EXTRA_POSITION, priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_location_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_location:
                saveLocation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
