package rishabh.notelocker.db;
import android.provider.BaseColumns;

/* This class defines constants to that are used to access dat in the database. */
public class AccessData {
    public static final String database_name = "com.rishabh.notelocker.db";
    public static final int database_version = 1;

    public class ToDoEntry implements BaseColumns{
        public static final String table = "todo";
        public static final String todo_title = "title";
    }
}
