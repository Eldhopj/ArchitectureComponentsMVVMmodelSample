package in.eldhopj.architecturecomponents_mvvmmodelsample.RoomDb.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import in.eldhopj.architecturecomponents_mvvmmodelsample.RoomDb.DataBase.NoteEntity;
import in.eldhopj.architecturecomponents_mvvmmodelsample.RoomDb.Repository.NoteRepository;

/**
 * ViewModel is for to store and process data from the repository to to UI and vice versa
 * The advantage of using viewModel to be a middle man is that , View model survives configuration changes
 * LifeCycle of view model @see <https://developer.android.com/topic/libraries/architecture/viewmodel#lifecycle/>
 */
public class NoteViewModel extends AndroidViewModel {

    //We have to pass to  member variables
    private NoteRepository repository;
    private LiveData<List<NoteEntity>> allnotes;

    //NOTE : we use application instead of context, because application even the activity is destroyed. if we use context there will be a memory leak
    public NoteViewModel(@NonNull Application application) {
        super(application);
        //Instantiate 2 member variables in constructor
        repository = new NoteRepository(application);
        allnotes = repository.getAllNotes();
    }


    public void insert(NoteEntity note) {
        repository.insert(note);
    }

    public void update(NoteEntity note) {
        repository.update(note);
    }

    public void delete(NoteEntity note) {
        repository.delete(note);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<NoteEntity>> getAllnotes() {
        return allnotes;
    }
}
