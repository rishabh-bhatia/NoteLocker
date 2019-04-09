package rishabh.notelocker.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenDatabase extends SQLiteOpenHelper{
    public OpenDatabase(Context context) {
        super(context, AccessData.database_name, null, AccessData.database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + AccessData.ToDoEntry.table + " ( " +
                AccessData.ToDoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AccessData.ToDoEntry.todo_title + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AccessData.ToDoEntry.table);
        onCreate(db);
    }
}
