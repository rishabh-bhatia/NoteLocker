package rishabh.notelocker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class EditNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        EditText editText = findViewById(R.id.editText4);

        Intent intent = getIntent();
        int noteid = intent.getIntExtra("noteid", -1);

        if(noteid != -1) {
            editText.setText(Notes.notes.get(noteid));
        }
    }
}
