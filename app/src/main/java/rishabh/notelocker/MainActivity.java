package rishabh.notelocker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onToDoClick(View v){
        Intent myIntent = new Intent(getBaseContext(),   ToDo.class);
        startActivity(myIntent);
    }

    public void onNoteClick(View v){
        Intent myIntent = new Intent(getApplicationContext(),   Notes.class);
        startActivity(myIntent);
    }
}
