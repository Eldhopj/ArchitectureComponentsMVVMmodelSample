package in.eldhopj.architecturecomponents_mvvmmodelsample;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Making instance of view model
        noteViewModel = ViewModelProviders.of(this) // to which activity lifecycle it is scoped to
                .get(NoteViewModel.class); //Getting instance of the particular view model class

        noteViewModel.getAllnotes()
                .observe(this, new Observer<List<NoteEntity>>() {
                    @Override
                    public void onChanged(@Nullable List<NoteEntity> noteEntities) { // This will trigger every time there is a data change
                        //Update RecyclerView
                    }
                });
    }

}
