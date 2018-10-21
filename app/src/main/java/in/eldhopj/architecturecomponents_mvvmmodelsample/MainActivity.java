package in.eldhopj.architecturecomponents_mvvmmodelsample;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import in.eldhopj.architecturecomponents_mvvmmodelsample.Adapter.RecyclerAdapter;
import in.eldhopj.architecturecomponents_mvvmmodelsample.RoomDb.DataBase.NoteEntity;
import in.eldhopj.architecturecomponents_mvvmmodelsample.RoomDb.ViewModel.NoteViewModel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String FROM_EDIT_NOTES ="editNote";
    public static final String EDIT_NOTE= "note";

    //Define the variables
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private NoteViewModel noteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
        gesture();
        //Making instance of view model
        noteViewModel = ViewModelProviders.of(this) // to which activity lifecycle it is scoped to
                .get(NoteViewModel.class); //Getting instance of the particular view model class

        noteViewModel.getAllnotes()
                .observe(this, new Observer<List<NoteEntity>>() {
                    @Override
                    public void onChanged(@Nullable List<NoteEntity> noteEntities) { // This will trigger every time there is a data change mListItems = noteEntities;
                        mRecyclerAdapter.setData(noteEntities);
                    }
                });

        mRecyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, NoteEntity note) {
                Intent intent = new Intent(getApplicationContext(),AddNoteActivity.class);
                intent.putExtra(FROM_EDIT_NOTES,true); // passes true if its for editing
                intent.putExtra(EDIT_NOTE,note);
                startActivity(intent);
            }
        });

    }

    //Create Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_deleteall_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Selection menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){ // get the ID
            case R.id.deleteAll:
                deleteAll();
                return true;

            default:
                return false;

        }
    }

    private void deleteAll() {
        noteViewModel.deleteAllNotes();
    }

    private void initRecyclerView(){
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

    /**
     * Swipe Right gesture to remove the item from the recycler view
     * */
    private void gesture(){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition(); // getting the adapter position
                noteViewModel.delete(mRecyclerAdapter.getNoteAt(position)); //Passing the position and getting the object in the particular position in return
            }
        }).attachToRecyclerView(mRecyclerView); // Attach it to the recycler view
    }

}
