package rishabh.notelocker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;
import android.view.View;

/* This class extends AppCompat because an AppCompat theme is used. */
/* Implementing the NavigationView.OnNavigationItemSelectedListener interface allows activity to
respond to the user's clicking options in the navigation drawer.*/
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }

    public void onToDoClick(View v){
        Intent myIntent = new Intent(getBaseContext(),   ToDo.class);
        startActivity(myIntent);
    }

    public void onNoteClick(View v){
        Intent myIntent = new Intent(getApplicationContext(),   Notes.class);
        startActivity(myIntent);
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
                intent = new Intent(this, ToDo.class);
        }

        startActivity(intent);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit NoteLocker?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }
}

