package in.eldhopj.architecturecomponents_mvvmmodelsample;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

/**Connect all different parts*/
@Database(entities = {NoteEntity.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "note_database";
    private static NoteDatabase instance;
    public abstract NoteDao noteDao() ;//uses to access Dao , where the Db operations are in

    public static synchronized NoteDatabase getInstance(Context context) { //
        if (instance == null) { // if the AppDatabase instance is not created ie, null create one
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, NoteDatabase.DATABASE_NAME)
                    // .allowMainThreadQueries() // WARNING : Queries should be done in a separate thread to avoid locking the UI, We will allow this ONLY TEMPORALLY to see that our DB is working
                    .fallbackToDestructiveMigration() //WARNING : Deletes existing Db while migrating into another version
                    .addCallback(roomCallback)// NOTE : Only for creating pre-filled values in the DB
                    .build(); // returns instance of this DB
        }
        return instance;
    }


    //---------------------Populating Dummy values-----------------------------//
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) { //onCreate calls only when DB is created
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    //AsyncTask for populating dummy values into DB
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        private PopulateDbAsyncTask(NoteDatabase db) {
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new NoteEntity("Title 1", "Description 1", 1));
            noteDao.insert(new NoteEntity("Title 2", "Description 2", 2));
            noteDao.insert(new NoteEntity("Title 3", "Description 3", 3));
            return null;
        }
    }
}
