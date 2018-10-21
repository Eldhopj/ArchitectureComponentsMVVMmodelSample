package in.eldhopj.architecturecomponents_mvvmmodelsample.RoomDb.DataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;


/**Create a database named note_table*/
@Entity(tableName = "note_table") //Note : if we didn't give "(tableName = "note_table")" the db name will be "NoteEntity"

public class NoteEntity implements Parcelable {

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



    //------------------------------Parcelable Stuffs---------------------------/

    protected NoteEntity(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        priority = in.readInt();
    }

    public static final Creator<NoteEntity> CREATOR = new Creator<NoteEntity>() {
        @Override
        public NoteEntity createFromParcel(Parcel in) {
            return new NoteEntity(in);
        }

        @Override
        public NoteEntity[] newArray(int size) {
            return new NoteEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(priority);
    }
}
