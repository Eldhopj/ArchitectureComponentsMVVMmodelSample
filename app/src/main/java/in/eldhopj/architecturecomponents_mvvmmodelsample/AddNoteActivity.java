package in.eldhopj.architecturecomponents_mvvmmodelsample;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;

import in.eldhopj.architecturecomponents_mvvmmodelsample.RoomDb.DataBase.NoteEntity;
import in.eldhopj.architecturecomponents_mvvmmodelsample.RoomDb.ViewModel.NoteViewModel;

public class AddNoteActivity extends AppCompatActivity {
    private EditText titleET;
    private EditText descET;
    private NumberPicker numberPickerPriority;
    private NoteViewModel noteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        titleET = findViewById(R.id.title);
        descET = findViewById(R.id.description);
        numberPickerPriority = findViewById(R.id.priorityPicker);

        numberPickerPriority.setMinValue(1); // Setting min value for number picker
        numberPickerPriority.setMaxValue(10);// Setting max value for number picker

        noteViewModel = ViewModelProviders.of(this) // to which activity lifecycle it is scoped to
                .get(NoteViewModel.class); //Getting instance of the particular view model class

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add notes");

            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close); // For back arrow on toolbar
            getSupportActionBar().setDisplayShowHomeEnabled(true);
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

        noteViewModel.insert(new NoteEntity(title,desc,priority));
    }
}
