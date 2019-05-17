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
import android.support.v7.widget.Toolbar;
import java.util.ArrayList;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.design.widget.NavigationView;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import rishabh.notelocker.db.AccessData;
import rishabh.notelocker.db.OpenDatabase;

/* This class consists of methods which make together a feature for adding and deleting a ToDo.*/
/* This class extends AppCompat because an AppCompat theme is used. */
/* Implementing the NavigationView.OnNavigationItemSelectedListener interface allows activity to
respond to the user's clicking options in the navigation drawer.*/
public class ToDo extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "ToDo"; // creating constant for logging
    private OpenDatabase OpenDB;    // a private instance of helper class
    private ListView todoListView;  // a private instance of ListView
    private ArrayAdapter<String> listAdapter; // this helps in populating listView with data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // setting toolbar as the activity's app bar
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        /* Adding drawer toggle for opening the navigation drawer by clicking on an icon in the toolbar. */
        /* Adding a drawer toggle by creating an instance of  ActionBarDrawerToggle class and adding it to the drawer layout. */
        /* The ActionBarDrawerToggle takes five parameters : current activity, activity's drawer layout, activity's toolbar,
        and ID of two string resources for opening and closing the drawer. */
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.nav_open_drawer,
                R.string.nav_close_drawer);
        /* Adding toggle to the drawer layout by calling the DrawerLayout AddDrawerLayout() method, and passing toggle as parameter. */
        drawer.addDrawerListener(toggle);
        /* Calling toggle's syncState() method to synchronize the icon on the toolbar with the state of the drawer. */
        toggle.syncState();
        /* Registering the activity as a listener on the navigation view so it will be notified if the user clicks on an item.
         * This is done by getting a reference to the navigation view, and calling it's navigationView.setNavigationItemSelectedListener() method. */
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /* Fetching data from the database and displaying it on screen */
        OpenDB = new OpenDatabase(this); // initializing instance of helper class
        todoListView = (ListView) findViewById(R.id.list_todo); // initializing the listView

        updateUI();

        SQLiteDatabase db = OpenDB.getReadableDatabase();
        Cursor cursor = db.query(AccessData.ToDoEntry.table,
                new String[]{AccessData.ToDoEntry._ID, AccessData.ToDoEntry.todo_title},
                null, null, null, null, null);
        while(cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(AccessData.ToDoEntry.todo_title);
            // using TAG to print message on logcat
            Log.d(TAG, "Task: " + cursor.getString(idx));
        }
        cursor.close();
        db.close();
    }

    /* This gets called when an item in the drawer is clicked. Parameter is the clicked item. */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;
        switch(id){

            case R.id.nav_todo:
                intent = new Intent(this, ToDo.class);
                break;

            case R.id.nav_settings:
                intent = new Intent(this, Settings.class);
                break;

            case R.id.nav_notes:
                intent = new Intent(this, Notes.class);
                break;

            case R.id.nav_info:
                intent = new Intent(this, Information.class);
                break;

            default:
                intent = new Intent(this, ToDo.class);
        }

        startActivity(intent);
        finish();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /* onCreateOptionMenu: Inflates the menu in to_do activity */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todo_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* onOptionsItemSelected: Responds to different user interaction with the menu item */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                final EditText taskEditText = new EditText(this);
                /* Adding an alert dialog box to get task from the user when the 'Add' button of menu item is clicked */
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Make a To-Do")
                        .setMessage("What would you like to add?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            /* Getting input from user when 'Add' button is clicked. And, storing it inside database */
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
                                updateUI(); /* Updating the todo list with new changes made to the database, and displaying it on screen. */
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

    /* Display the changes made to the database using an Adapter.*/
    private void updateUI() {
        /* Making an array of strings to store tasks entered by the user. */
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = OpenDB.getReadableDatabase();
        Cursor cursor = db.query(AccessData.ToDoEntry.table,
                new String[]{AccessData.ToDoEntry._ID, AccessData.ToDoEntry.todo_title},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(AccessData.ToDoEntry.todo_title);
            taskList.add(cursor.getString(idx));
        }

        /* Check if array adapter is created. */
        if (listAdapter == null) {
            /* If adapter is not created i.e. NULL, create a new adapter */
            listAdapter = new ArrayAdapter<>(this,
                    R.layout.todo_item,
                    R.id.task_title,
                    taskList);
            /* Set the above created adapter as the todoListView adapter */
            todoListView.setAdapter(listAdapter);
        } else {
            /* If created: it is assigned to the todoListView */
            listAdapter.clear(); // clear it
            listAdapter.addAll(taskList); // re-populate it
            listAdapter.notifyDataSetChanged(); // notify view to refresh with new data values
        }

        cursor.close();
        db.close();
    }

    /* Deletes an item from ToDo list. This method gets called when user taps on 'Done' button.*/
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