package in.eldhopj.architecturecomponents_mvvmmodelsample;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

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
                    .build(); // returns instance of this DB
        }
        return instance;
    }
}
