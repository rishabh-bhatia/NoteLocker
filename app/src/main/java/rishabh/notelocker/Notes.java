package rishabh.notelocker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashSet;

/* This class extends AppCompat because an AppCompat theme is used. */
/* Implementing the NavigationView.OnNavigationItemSelectedListener interface allows activity to
respond to the user's clicking options in the navigation drawer.*/
public class Notes extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter aa;

    //In this section we're adding Items to the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.note_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /*Decides actions when an item is selected, in this case when a new note is added from menuItem
     then a new activity is started which will allow the user to* edit their note./
      */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //When the item that has been selected is add_note, start a new activity
        if(item.getItemId() == R.id.add_note){
            Intent intent = new Intent(getApplicationContext(), EditNoteActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

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

        ListView listView = findViewById(R.id.listview);
        //Check existence of set and display set if exists from sharedpreferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("rishabh.notelocker", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);
        if(set == null){
            Toast.makeText(this, "No note found", Toast.LENGTH_LONG).show();//If the set is null then a toast notification is shown
        }
        else{
            notes = new ArrayList(set);//If the set is not null then the Hashset will be shown from sharedPreferences
        }

        aa = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(aa);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent myIntent = new Intent(getApplicationContext(),   EditNoteActivity.class);
                myIntent.putExtra("noteRefId", i);
                startActivity(myIntent);
            }
        });

        //Sets action to take place when a list item is long pressed
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int delItem = i;

                //Deletes an item after confirming if you'd like to delete the note
                new AlertDialog.Builder(Notes.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Are you sure?")
                        .setMessage("Delete this note").setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        notes.remove(delItem);
                        aa.notifyDataSetChanged();

                        //To change our set when an item is deleted
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("rishabh.notelocker", Context.MODE_PRIVATE);

                        HashSet<String> set = new HashSet(Notes.notes);
                        sharedPreferences.edit().putStringSet("notes", set).apply();
                    }
                }).show();


                return true;
            }
        });
    }

    /* This gets called when an item in the drawer is clicked. Parameter is the clicked item. */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;
        switch(id){

            case R.id.home_h:
                intent = new Intent(this, MainActivity.class);
                break;

            case R.id.nav_settings:
                intent = new Intent(this, Settings.class);
                break;


            case R.id.nav_info:
                intent = new Intent(this, Information.class);
                break;

            default:
                intent = new Intent(this, Notes.class);
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

}
