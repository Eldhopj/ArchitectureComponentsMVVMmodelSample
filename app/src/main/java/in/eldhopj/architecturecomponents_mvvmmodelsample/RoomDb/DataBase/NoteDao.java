package in.eldhopj.architecturecomponents_mvvmmodelsample.RoomDb.DataBase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 *DAO must be an interface since we just create method and annotate it, and Room will auto generate necessary codes for it
 *
 *  NOTE : Its recommenced to use different DAO interfaces for different set of operations
 * For more SQL queries -> @see <a href="https://github.com/googlecodelabs/android-persistence/blob/master/app/src/main/java/com/example/android/persistence/codelab/db/UserDao.java"/>
 * */

@Dao // Give Data Access Object annotation
public interface NoteDao {
    @Insert
    void insert(NoteEntity note);

    @Update(onConflict = OnConflictStrategy.REPLACE) // to replace in-case of any conflicts
    void update(NoteEntity note);

    @Delete
    void delete(NoteEntity note);

    @Query("DELETE FROM note_table") // Delete all notes from table
    void deleteAllNotes();

    @Query("SELECT * FROM note_table ORDER BY priority_number DESC")
    LiveData<List<NoteEntity>> getAllNotes();// Observe the object , so if there is any changes in the table this value will be auto updated and the activity will be notified
}
