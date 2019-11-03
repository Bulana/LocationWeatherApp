package com.bulana.locationsection;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Location.class}, version = 1)
public abstract class LocationDatabase extends RoomDatabase {

    private static LocationDatabase instance;

    public abstract LocationDao locationDao();

    public static synchronized LocationDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    LocationDatabase.class, "location_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private LocationDao locationDao;

        private PopulateDbAsyncTask(LocationDatabase db) {
            locationDao = db.locationDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            locationDao.insert(new Location("CAPE TOWN", "32  \u2103", "Partly Cloudy",1));
            locationDao.insert(new Location("PORT ELIZABETH", "56  \u2103", "Rainy",2));
            locationDao.insert(new Location("JOHNNESBURG", "80  \u2103", "Windy",3));
            locationDao.insert(new Location("DURBAN", "20  \u2103", "Sand",4));
            locationDao.insert(new Location("KIMBERLY", "56  \u2103", "Sunny",5));
            locationDao.insert(new Location("RUSTERNBURG", "25  \u2103", "Partly Coudy",6));
            locationDao.insert(new Location("NELSPRUIT", "34  \u2103", "Rainy",7));
            locationDao.insert(new Location("POLOKWANE", "60  \u2103", "Partly Cloudy",8));
            locationDao.insert(new Location("BLOOMFONTEIN", "65  \u2103", "Partly Cloudy",9));
            locationDao.insert(new Location("CAPE TOWN", "23  \u2103", "Rainy",10));
            locationDao.insert(new Location("PORT ELIZABETH", "56  \u2103", "Partly Cloudy",11));
            locationDao.insert(new Location("JOHNNESBURG", "18  \u2103", "Partly Cloudy",12));
            return null;
        }
    }
}
