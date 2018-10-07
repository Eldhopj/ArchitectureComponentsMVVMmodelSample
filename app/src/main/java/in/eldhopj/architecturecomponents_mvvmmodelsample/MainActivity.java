package in.eldhopj.architecturecomponents_mvvmmodelsample;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import in.eldhopj.architecturecomponents_mvvmmodelsample.Adapter.RecyclerAdapter;
import in.eldhopj.architecturecomponents_mvvmmodelsample.RoomDb.DataBase.NoteEntity;
import in.eldhopj.architecturecomponents_mvvmmodelsample.RoomDb.ViewModel.NoteViewModel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //Define the variables
    private RecyclerView mRecyclerView;
    private List<NoteEntity> mListItems;
    private RecyclerAdapter mRecyclerAdapter;
    private NoteViewModel noteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListItems = new ArrayList<>();
        initRecyclerView();
        //Making instance of view model
        noteViewModel = ViewModelProviders.of(this) // to which activity lifecycle it is scoped to
                .get(NoteViewModel.class); //Getting instance of the particular view model class

        noteViewModel.getAllnotes()
                .observe(this, new Observer<List<NoteEntity>>() {
                    @Override
                    public void onChanged(@Nullable List<NoteEntity> noteEntities) { // This will trigger every time there is a data changemListItems = noteEntities;
                        mRecyclerAdapter.setData(noteEntities);
                    }
                });

    }

    private void initRecyclerView(){
        /**bind with xml*/
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true); // setting it to true allows some optimization to our view , avoiding validations when mRecyclerAdapter content changes

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)); //it can be GridLayoutManager or StaggeredGridLayoutManager
        mRecyclerAdapter = new RecyclerAdapter(this); //set the mRecyclerAdapter to the recycler view
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL)); // Divider decorations
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    public void addNotes(View view) {
        Intent intent = new Intent(getApplicationContext(),AddNoteActivity.class);
        startActivity(intent);
    }
}
