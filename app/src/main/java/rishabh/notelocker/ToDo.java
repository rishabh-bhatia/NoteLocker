package rishabh.notelocker;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import rishabh.notelocker.db.AccessData;
import rishabh.notelocker.db.OpenDatabase;


public class ToDo extends AppCompatActivity {

    private static final String TAG = "ToDo";
    private OpenDatabase OpenDB;
    private ListView todoListView;
    private ArrayAdapter<String> listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        OpenDB = new OpenDatabase(this);
        todoListView = (ListView) findViewById(R.id.list_todo);
        updateUI();

        SQLiteDatabase db = OpenDB.getReadableDatabase();
        Cursor cursor = db.query(AccessData.ToDoEntry.table,
                new String[]{AccessData.ToDoEntry._ID, AccessData.ToDoEntry.todo_title},
                null, null, null, null, null);
        while(cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(AccessData.ToDoEntry.todo_title);
            Log.d(TAG, "Task: " + cursor.getString(idx));
        }
        cursor.close();
        db.close();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todo_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Make a To-Do")
                        .setMessage("What would you like to add?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                SQLiteDatabase db = OpenDB.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(AccessData.ToDoEntry.todo_title, task);
                                db.insertWithOnConflict(AccessData.ToDoEntry.table,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Display the changes made to the database.*/
    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = OpenDB.getReadableDatabase();
        Cursor cursor = db.query(AccessData.ToDoEntry.table,
                new String[]{AccessData.ToDoEntry._ID, AccessData.ToDoEntry.todo_title},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(AccessData.ToDoEntry.todo_title);
            taskList.add(cursor.getString(idx));
        }

        if (listAdapter == null) {
            listAdapter = new ArrayAdapter<>(this,
                    R.layout.todo_item,
                    R.id.task_title,
                    taskList);
            todoListView.setAdapter(listAdapter);
        } else {
            listAdapter.clear();
            listAdapter.addAll(taskList);
            listAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }

    /* Deletes an item from ToDo list.*/
    public void deleteToDo(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = OpenDB.getWritableDatabase();
        db.delete(AccessData.ToDoEntry.table,
                AccessData.ToDoEntry.todo_title + " = ?",
                new String[]{task});
        db.close();
        updateUI();
    }
}