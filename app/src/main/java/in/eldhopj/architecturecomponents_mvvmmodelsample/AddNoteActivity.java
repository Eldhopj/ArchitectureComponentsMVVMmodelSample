package in.eldhopj.architecturecomponents_mvvmmodelsample;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;

import in.eldhopj.architecturecomponents_mvvmmodelsample.RoomDb.DataBase.NoteEntity;
import in.eldhopj.architecturecomponents_mvvmmodelsample.RoomDb.ViewModel.NoteViewModel;

import static in.eldhopj.architecturecomponents_mvvmmodelsample.MainActivity.EDIT_NOTE;
import static in.eldhopj.architecturecomponents_mvvmmodelsample.MainActivity.FROM_EDIT_NOTES;

public class AddNoteActivity extends AppCompatActivity {
    private static final String TAG = "AddNoteActivity";
    private EditText titleET;
    private EditText descET;
    private NumberPicker numberPickerPriority;
    private NoteViewModel noteViewModel;
    boolean editItem;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        // Getting intent values from Main Activity
        Intent intent = getIntent();
        NoteEntity clickedItem = intent.getParcelableExtra(EDIT_NOTE); //The values inside the clicked item object is in the clicked item
        editItem = intent.getBooleanExtra(FROM_EDIT_NOTES,false);

        titleET = findViewById(R.id.title);
        descET = findViewById(R.id.description);
        numberPickerPriority = findViewById(R.id.priorityPicker);

        numberPickerPriority.setMinValue(1); // Setting min value for number picker
        numberPickerPriority.setMaxValue(10);// Setting max value for number picker

        noteViewModel = ViewModelProviders.of(this) // to which activity lifecycle it is scoped to
                .get(NoteViewModel.class); //Getting instance of the particular view model class

        if (getSupportActionBar() != null) {

            if (!editItem)
                getSupportActionBar().setTitle("Add notes");
            else
                getSupportActionBar().setTitle("Edit notes");

            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close); // For back arrow on toolbar
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if (intent.hasExtra(EDIT_NOTE)){ // check if the intent contain values
            titleET.setText(clickedItem.getTitle());
            descET.setText(clickedItem.getDescription());
            numberPickerPriority.setValue(clickedItem.getPriority());
            id = clickedItem.getId(); // NOTE : Primary key is needed to update th value in that position , else Room wont know which value to be updated
        }
    }
    //Create Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_note_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //Selection menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){ // get the ID
            case R.id.save_note:
                saveNotes();
                return true;

            default:
                return false;

        }
    }

    //TODO : do validation checks , Here i am not doing any validation checks for the simplicity
    private void saveNotes() {

        String title = titleET.getText().toString().trim();
        String desc = descET.getText().toString().trim();
        int priority = numberPickerPriority.getValue();

        NoteEntity note = new NoteEntity(title,desc,priority);

        if (editItem){
            note.setId(id); // passing primary Key, to know which item to be updated
            noteViewModel.update(note);
        }
        else {
            noteViewModel.insert(note);
        }
        finish();
    }
}
