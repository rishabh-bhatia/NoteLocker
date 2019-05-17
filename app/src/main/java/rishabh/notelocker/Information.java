package rishabh.notelocker;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/* This class extends AppCompat because an AppCompat theme is used. */
/* Implementing the NavigationView.OnNavigationItemSelectedListener interface allows activity to
respond to the user's clicking options in the navigation drawer.*/
public class Information extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
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

    /* This gets called when an item in the drawer is clicked. Parameter is the clicked item. */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;
        switch (id) {

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
                intent = new Intent(this, Information.class);
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

