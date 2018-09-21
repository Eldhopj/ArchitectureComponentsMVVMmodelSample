package in.eldhopj.architecturecomponents_mvvmmodelsample;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * Repository is a way to abstract all the database operations including in n/w and provides a clean API to the rest of the app
 * */
public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<NoteEntity>> allNotes;

    // Application is a subClass of context; application context stays same throughout the lifecycle of the app so we pass application context for something live longer than an activity or service
    public NoteRepository(Application application){
        NoteDatabase database = NoteDatabase.getInstance(application); // the the current instance of the NoteDatabase
        noteDao = database.noteDao(); // Room auto generate all the necessary codes for this abstract class

        allNotes = noteDao.getAllNotes(); //get all notes
    }

    //------------------API's that exposed to the outside-------------------------//
    public void insert(NoteEntity note) {
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void update(NoteEntity note) {
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(NoteEntity note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    //NOTE : LiveData will execute all the db operations in the background thread, so there is no need of Async task
    public LiveData<List<NoteEntity>> getAllNotes() {
        return allNotes;
    }

    //--------------------Background tasks--------------------------//

    private static class InsertNoteAsyncTask extends AsyncTask<NoteEntity,Void,Void>{
        private NoteDao noteDao; // Required to make Db operations

        private InsertNoteAsyncTask(NoteDao noteDao) { // Make a constructor to pass the values to a static class
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDao.insert(noteEntities [0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<NoteEntity, Void, Void> {
        private NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(NoteEntity... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<NoteEntity, Void, Void> {
        private NoteDao noteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(NoteEntity... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> { //NOTE : we don't wanna pass our Note entity
        private NoteDao noteDao;

        private DeleteAllNotesAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}
