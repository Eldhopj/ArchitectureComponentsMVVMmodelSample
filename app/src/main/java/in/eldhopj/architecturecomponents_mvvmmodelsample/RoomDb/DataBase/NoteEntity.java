package in.eldhopj.architecturecomponents_mvvmmodelsample.RoomDb.DataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


/**Create a database named note_table*/
@Entity(tableName = "note_table") //Note : if we didn't give "(tableName = "note_table")" the db name will be "NoteEntity"

public class NoteEntity {

    /**
     * Room will autoGenerate columns for these fields
     */
    @PrimaryKey(autoGenerate = true)//Room will auto generate the ID , we don't wanna worry about it
    private int id;
    private String title;
    private String description;
    @ColumnInfo(name = "priority_number") //column name will be priority_number
    private int priority;

    //@Ignore -> for ignoring

    public NoteEntity(String title, String description, int priority) { //NOTE : We don't wanna pass ID , Since its auto generating
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    //NOTE : If a variable is not in the constructor it must have a setter method
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}
