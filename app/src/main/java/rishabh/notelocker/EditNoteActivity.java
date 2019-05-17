package rishabh.notelocker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

/* This class extends AppCompat because an AppCompat theme is used. */
public class EditNoteActivity extends AppCompatActivity {
    int noteRefId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        EditText editText = findViewById(R.id.editText4);

        Intent intent = getIntent();
        noteRefId = intent.getIntExtra("noteRefId", -1);


        if(noteRefId != -1) {
            editText.setText(Notes.notes.get(noteRefId));
        }
        //Add new notes
        else{
            Notes.notes.add("");
            noteRefId = Notes.notes.size() - 1;
            Notes.aa.notifyDataSetChanged();//Notifies the Array Adapter that data has changed
        }

        //Changes text in a specific note everytime that note's EditText changes text
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //No change required
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Notes.notes.set(noteRefId, String.valueOf(charSequence));
                Notes.aa.notifyDataSetChanged();
                //This is to get access to our app data
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("rishabh.notelocker", Context.MODE_PRIVATE);

                HashSet<String> set = new HashSet(Notes.notes);
                sharedPreferences.edit().putStringSet("notes", set).apply();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //No change Required
            }
        });
    }
}
