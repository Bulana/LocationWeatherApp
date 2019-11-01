package com.bulana.locationsection;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class LocationRepository {
    private LocationDao locationDao;
    private LiveData<List<Location>> allLocations;

    public LocationRepository(Application application) {
        LocationDatabase database = LocationDatabase.getInstance(application);
        locationDao = database.locationDao();
        allLocations = locationDao.getAllLocations();
    }

    public void insert(Location location) {
        new InsertLocationAsyncTask(locationDao).execute(location);
    }

    public void update(Location location) {
        new UpdateLocationAsyncTask(locationDao).execute(location);
    }

    public void delete(Location location) {
        new DeleteLocationAsyncTask(locationDao).execute(location);
    }

    public void deleteAllLocations() {
        new DeleteAllLocationsAsyncTask(locationDao).execute();
    }

    public LiveData<List<Location>> getAllLocations() {
        return allLocations;
    }

    private static class InsertLocationAsyncTask extends AsyncTask<Location, Void, Void> {
        private LocationDao locationDao;

        private InsertLocationAsyncTask(LocationDao locationDao) {
            this.locationDao = locationDao;
        }

        @Override
        protected Void doInBackground(Location... locations) {
            locationDao.insert(locations[0]);
            return null;
        }
    }

    private static class UpdateLocationAsyncTask extends AsyncTask<Location, Void, Void> {
        private LocationDao locationDao;

        private UpdateLocationAsyncTask(LocationDao locationDao) {
            this.locationDao = locationDao;
        }

        @Override
        protected Void doInBackground(Location... locations) {
            locationDao.update(locations[0]);
            return null;
        }
    }

    private static class DeleteLocationAsyncTask extends AsyncTask<Location, Void, Void> {
        private LocationDao locationDao;

        private DeleteLocationAsyncTask(LocationDao locationDao) {
            this.locationDao = locationDao;
        }

        @Override
        protected Void doInBackground(Location... locations) {
            locationDao.delete(locations[0]);
            return null;
        }
    }

    private static class DeleteAllLocationsAsyncTask extends AsyncTask<Void, Void, Void> {
        private LocationDao locationDao;

        private DeleteAllLocationsAsyncTask(LocationDao locationDao) {
            this.locationDao = locationDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            locationDao.deleteAllLocations();
            return null;
        }
    }
}
