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

import java.util.ArrayList;
import java.util.HashSet;

public class Notes extends AppCompatActivity {

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

        ListView listView = findViewById(R.id.listview);

        //Check existence of set and display set if exists from sharedpreferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("rishabh.notelocker", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);

        if(set == null){
            notes.add("Example note");//If the set is null then a demo note is displayed
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
}
