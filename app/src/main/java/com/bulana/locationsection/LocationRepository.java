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
        new InsertNoteAsyncTask(locationDao).execute(location);
    }

    public void update(Location location) {
        new UpdateNoteAsyncTask(locationDao).execute(location);
    }

    public void delete(Location location) {
        new DeleteNoteAsyncTask(locationDao).execute(location);
    }

    public void deleteAllLocations() {
        new DeleteAllLocationsAsyncTask(locationDao).execute();
    }

    public LiveData<List<Location>> getAllLocations() {
        return allLocations;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Location, Void, Void> {
        private LocationDao locationDao;

        private InsertNoteAsyncTask(LocationDao locationDao) {
            this.locationDao = locationDao;
        }

        @Override
        protected Void doInBackground(Location... locations) {
            locationDao.insert(locations[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Location, Void, Void> {
        private LocationDao locationDao;

        private UpdateNoteAsyncTask(LocationDao locationDao) {
            this.locationDao = locationDao;
        }

        @Override
        protected Void doInBackground(Location... locations) {
            locationDao.update(locations[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Location, Void, Void> {
        private LocationDao locationDao;

        private DeleteNoteAsyncTask(LocationDao locationDao) {
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
