package rishabh.notelocker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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

import java.util.ArrayList;
import java.util.HashSet;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

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

    //Decides actions when an item is selected, in this case when a new note is added from menuItem then a new activity is started
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.nav_open_drawer,
                R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
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
